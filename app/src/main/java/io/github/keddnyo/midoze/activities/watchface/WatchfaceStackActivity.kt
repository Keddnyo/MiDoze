package io.github.keddnyo.midoze.activities.watchface

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.WatchfaceCommonAdapter
import io.github.keddnyo.midoze.adapters.WatchfaceStackAdapter
import io.github.keddnyo.midoze.local.dataModels.Watchface
import io.github.keddnyo.midoze.local.devices.WatchfaceRepository.watchfaceDeviceStack
import io.github.keddnyo.midoze.local.menu.Dimens.CARD_GRID_WIDTH
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

class WatchfaceStackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.menu_watchface)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchface_common)

        val context = this@WatchfaceStackActivity
        val refreshWatchfaceLayout: SwipeRefreshLayout = findViewById(R.id.refreshWatchfaceLayout)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val watchfaceStackAdapter = WatchfaceStackAdapter()
        val gson = Gson()

        OnlineStatus(context).run {
            class GetWatchfaceList : AsyncTask() {
                override fun execute() {
                    super.execute()

                    var watchfaceArrayList: ArrayList<Watchface.WatchfaceDataArrayGlobal> = arrayListOf()
                    val watchfaceArrayListSlot: ArrayList<Watchface.WatchfaceDataArrayGlobal> = arrayListOf()

                    Executors.newSingleThreadExecutor().execute {
                        prefs.getString("watchfaceStackCache", "").toString().let { watchfaceStackCache ->
                            if (watchfaceStackCache.isNotBlank() && watchfaceStackCache != "null") {
                                watchfaceArrayList = GsonBuilder().create().fromJson(
                                    watchfaceStackCache,
                                    object : TypeToken<ArrayList<Watchface.WatchfaceDataArrayGlobal>>() {}.type
                                )
                            } else if (isOnline) {
                                if (watchfaceStackAdapter.itemCount == 0) {
                                    watchfaceArrayList
                                } else {
                                    watchfaceArrayListSlot
                                }.let { arrayList ->
                                    for (a in watchfaceDeviceStack) {
                                        val content = runBlocking(Dispatchers.IO) {
                                            Requests().getWatchfaceData(a.deviceAlias)
                                        }

                                        val json = JSONObject(content)
                                        val data = json.getJSONArray("data")

                                        val watchfaceArrayStack = arrayListOf<Watchface.WatchfaceDataArray>()

                                        for (d in 0 until data.length()) {
                                            val list = data.getJSONObject(d).getJSONArray("list")
                                            val watchfaceArray = arrayListOf<Watchface.WatchfaceData>()

                                            for (l in 0 until list.length()) {
                                                val url = URL(list.getJSONObject(l).getString("icon"))
                                                val preview = BitmapFactory.decodeStream(url.openConnection().getInputStream())

                                                fun getItem(name: String) = list.getJSONObject(l).getString(name)

                                                BitmapCache(context).encode(a.deviceAlias, getItem("display_name"), preview)

                                                watchfaceArray.add(
                                                    Watchface.WatchfaceData(
                                                        title = getItem("display_name"),
                                                        categoryName = data.getJSONObject(d).getString("tab_name"),
                                                        deviceName = a.deviceName,
                                                        deviceAlias = a.deviceAlias,
                                                        introduction = getItem("introduction"),
                                                        url = getItem("config_file")
                                                    )
                                                )
                                            }

                                            watchfaceArrayStack.add(
                                                Watchface.WatchfaceDataArray(
                                                    title = data.getJSONObject(d).getString("tab_name"),
                                                    stack = watchfaceArray,
                                                    hasCategories = a.hasCategories
                                                )
                                            )
                                        }

                                        arrayList.add(
                                            Watchface.WatchfaceDataArrayGlobal(
                                                title = a.deviceName,
                                                alias = a.deviceAlias,
                                                stack = watchfaceArrayStack
                                            )
                                        )

                                        watchfaceArrayListSlot.let {
                                            if (it.isNotEmpty() && it != watchfaceArrayList) {
                                                watchfaceArrayList = it
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        mainHandler.post {
                            findViewById<RecyclerView>(R.id.watchfaceCommonRecyclerView).let { RecyclerView ->
                                RecyclerView.layoutManager = GridLayoutManager(
                                    context, Display()
                                        .getGridLayoutIndex(context, CARD_GRID_WIDTH)
                                )

                                WatchfaceCommonAdapter().let { adapter ->
                                    RecyclerView.adapter = adapter

                                    adapter.addWatchfaceList(
                                        watchfaceArrayList
                                    )
                                }
                            }

                            refreshWatchfaceLayout.isRefreshing = false
                        }

                        prefs.edit().putString("watchfaceStackCache", gson.toJson(watchfaceArrayList).toString()).apply()
                    }
                }
            }

            PackageUtils(context, context.packageName).getPackageVersionBuild().let {
                if (prefs.getInt("VERSION_CODE", 0) != it) {
                    editor.apply {
                        putInt("VERSION_CODE", it)
                        remove("watchfaceStackCache")
                        apply()
                    }
                }
            }

            GetWatchfaceList().run {
                refreshWatchfaceLayout.setOnRefreshListener {
                    if (isOnline) {
                        editor.apply {
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}