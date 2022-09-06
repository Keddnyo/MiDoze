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
import io.github.keddnyo.midoze.adapters.watchface.WatchfaceCommonAdapter
import io.github.keddnyo.midoze.local.dataModels.WatchfaceData
import io.github.keddnyo.midoze.local.devices.WatchfaceRepository.DeviceStacks
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
        setContentView(R.layout.activity_watchfaces)

        val context = this@WatchfaceStackActivity
        val refreshWatchfaceLayout: SwipeRefreshLayout = findViewById(R.id.refreshWatchfaceLayout)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val gson = Gson()

        OnlineStatus(context).run {
            class GetWatchfaceList : AsyncTask() {
                override fun execute() {
                    super.execute()

                    var watchfaceArrayStack: ArrayList<WatchfaceData.WatchfaceArray> = arrayListOf()

                    Executors.newSingleThreadExecutor().execute {
                        prefs.getString("watchfaceStackCache", "").toString().let { watchfaceStackCache ->
                            if (watchfaceStackCache.isNotBlank() && watchfaceStackCache != "null") {
                                watchfaceArrayStack = GsonBuilder().create().fromJson(
                                    watchfaceStackCache,
                                    object : TypeToken<ArrayList<WatchfaceData.WatchfaceArray>>() {}.type
                                )
                            } else if (isOnline) {
                                BitmapCache(context).clearCache()

                                for (device in DeviceStacks) {
                                    val watchfaceArray: ArrayList<WatchfaceData.Watchface> = arrayListOf()

                                    val content = runBlocking(Dispatchers.IO) {
                                        Requests().getWatchfaceData(device.alias)
                                    }

                                    val json = JSONObject(content)
                                    val data = json.getJSONArray("data")

                                    for (d in 0 until data.length()) {
                                        val list = data.getJSONObject(d).getJSONArray("list")

                                        for (l in 0 until list.length()) {
                                            val url = URL(list.getJSONObject(l).getString("icon"))
                                            val preview = BitmapFactory.decodeStream(url.openConnection().getInputStream())

                                            fun getItem(name: String) = list.getJSONObject(l).getString(name)

                                            BitmapCache(context).encode(device.alias, getItem("display_name"), preview)

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
                            findViewById<RecyclerView>(R.id.watchfaceCommonRecyclerView).let { RecyclerView ->
                                RecyclerView.layoutManager = GridLayoutManager(
                                    context, Display()
                                        .getGridLayoutIndex(context, CARD_GRID_WIDTH)
                                )

                                WatchfaceCommonAdapter().let { adapter ->
                                    RecyclerView.adapter = adapter

                                    adapter.addWatchfaceList(
                                        watchfaceArrayStack
                                    )
                                }
                            }

                            refreshWatchfaceLayout.isRefreshing = false
                        }

                        prefs.edit().putString("watchfaceStackCache", gson.toJson(watchfaceArrayStack).toString()).apply()
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