package io.github.keddnyo.midoze.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.watchface.WatchfaceAdapter
import io.github.keddnyo.midoze.local.dataModels.WatchfaceData
import io.github.keddnyo.midoze.local.repositories.WatchfaceRepository.DeviceStacks
import io.github.keddnyo.midoze.local.menu.Dimens.CARD_GRID_WIDTH
import io.github.keddnyo.midoze.local.menu.Dimens.SWIPE_LAYOUT_REFRESH_LENGTH
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

class WatchfaceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_watchface,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(requireActivity()) {
        super.onViewCreated(view, savedInstanceState)

        val refreshWatchfaceLayout: SwipeRefreshLayout = findViewById(R.id.refreshWatchfaceLayout)
        val watchfaceEmptyResponse: ConstraintLayout = findViewById(R.id.watchfaceEmptyResponse)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val gson = Gson()

        refreshWatchfaceLayout.setDistanceToTriggerSync(SWIPE_LAYOUT_REFRESH_LENGTH)

        OnlineStatus(this).run {
            class GetWatchfaceList : AsyncTask() {
                override fun execute() {
                    super.execute()

                    var watchfaceArrayStack: ArrayList<WatchfaceData.WatchfaceArray> = arrayListOf()

                    Executors.newSingleThreadExecutor().execute {
                        prefs.getString("watchfaceStackCache", "").toString()
                            .let { watchfaceStackCache ->
                                if (watchfaceStackCache.isNotBlank() && watchfaceStackCache != "null") {
                                    watchfaceArrayStack = GsonBuilder().create().fromJson(
                                        watchfaceStackCache,
                                        object :
                                            TypeToken<ArrayList<WatchfaceData.WatchfaceArray>>() {}.type
                                    )
                                } else if (isOnline()) {
                                    BitmapCache(context).clearCache()

                                    for (device in DeviceStacks) {
                                        val watchfaceArray: ArrayList<WatchfaceData.Watchface> =
                                            arrayListOf()

                                        val content = runBlocking(Dispatchers.IO) {
                                            Requests().getWatchfaceData(device.alias)
                                        }

                                        val json = JSONObject(content)
                                        val data = json.getJSONArray("data")

                                        for (d in 0 until data.length()) {
                                            val list = data.getJSONObject(d).getJSONArray("list")

                                            for (l in 0 until list.length()) {
                                                val url =
                                                    URL(list.getJSONObject(l).getString("icon"))
                                                val preview = BitmapFactory.decodeStream(
                                                    url.openConnection().getInputStream()
                                                )

                                                fun getItem(name: String) =
                                                    list.getJSONObject(l).getString(name)

                                                BitmapCache(context).encode(
                                                    device.alias,
                                                    getItem("display_name"),
                                                    preview
                                                )

                                                WatchfaceData.Watchface(
                                                    alias = device.alias,
                                                    title = getItem("display_name"),
                                                    url = getItem("config_file"),
                                                    watchfaceData = list.getJSONObject(l)
                                                ).let { watchface ->
                                                    if (watchface !in watchfaceArray) {
                                                        watchfaceArray.add(
                                                            watchface
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        watchfaceArrayStack.add(
                                            WatchfaceData.WatchfaceArray(
                                                name = device.name,
                                                watchface = watchfaceArray
                                            )
                                        )
                                    }
                                }
                            }

                        mainHandler.post {
                            WatchfaceAdapter(watchfaceArrayStack).let { adapter ->
                                if (adapter.itemCount == 0) {
                                    watchfaceEmptyResponse.visibility = View.VISIBLE
                                } else {
                                    watchfaceEmptyResponse.visibility = View.GONE
                                }

                                findViewById<RecyclerView>(R.id.watchfaceCommonRecyclerView).apply {
                                    layoutManager = GridLayoutManager(
                                        requireActivity(), Display()
                                            .getGridLayoutIndex(requireActivity(), CARD_GRID_WIDTH)
                                    )
                                    this.adapter = adapter
                                }

                            }

                            refreshWatchfaceLayout.isRefreshing = false
                        }

                        prefs.edit().putString(
                            "watchfaceStackCache",
                            gson.toJson(watchfaceArrayStack).toString()
                        ).apply()
                    }
                }
            }

            PackageUtils(context, context.packageName).getPackageVersionBuild().let {
                if (prefs.getInt("VERSION_CODE", 0) != it) {
                    editor.apply {
                        if (it != null) {
                            putInt("VERSION_CODE", it)
                            remove("watchfaceStackCache")
                            apply()
                        }
                    }
                }
            }

            GetWatchfaceList().run {
                refreshWatchfaceLayout.setOnRefreshListener {
                    if (isOnline()) {
                        editor.run {
                            remove("watchfaceStackCache")
                            apply()
                        }
                    }

                    execute()
                }

                refreshWatchfaceLayout.isRefreshing = true
                execute()
            }
        }
    }
}