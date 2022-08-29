package io.github.keddnyo.midoze.activities

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.DeviceStackAdapter
import io.github.keddnyo.midoze.adapters.WatchfaceCommonAdapter
import io.github.keddnyo.midoze.adapters.WatchfaceStackAdapter
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.github.keddnyo.midoze.local.dataModels.Watchface
import io.github.keddnyo.midoze.local.dataModels.WatchfaceCommonStack
import io.github.keddnyo.midoze.local.dataModels.WatchfaceStack
import io.github.keddnyo.midoze.local.devices.WatchfaceRepository.watchfaceDeviceStack
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.OnlineStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

class WatchfaceStackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_watchface)

        val context = this@WatchfaceStackActivity
        val refreshWatchfaceLayout: SwipeRefreshLayout = findViewById(R.id.refreshWatchfaceLayout)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val watchfaceStackAdapter = WatchfaceStackAdapter()
        val gson = Gson()

        OnlineStatus(context).run {
            var watchfaceArrayList: ArrayList<WatchfaceCommonStack> = arrayListOf()
            val watchfaceArrayListSlot: ArrayList<WatchfaceCommonStack> = arrayListOf()

            Thread {
                runOnUiThread {
                    refreshWatchfaceLayout.isRefreshing = true
                }

                prefs.getString("watchfaceStackCache", "").toString().let { watchfaceStackCache ->
                    if (watchfaceStackCache.isNotEmpty()) {
                        watchfaceArrayList = GsonBuilder().create().fromJson(
                            watchfaceStackCache,
                            object : TypeToken<ArrayList<WatchfaceCommonStack>>() {}.type
                        )
                    } else if (isOnline) {
                        if (watchfaceStackAdapter.itemCount == 0) {
                            watchfaceArrayList
                        } else {
                            watchfaceArrayListSlot
                        }.let { arrayList ->
                            for (a in watchfaceDeviceStack) {
                                val content = runBlocking(Dispatchers.IO) {
                                    Requests().getWatchfaceData(a.alias)
                                }

                                val json = JSONObject(content)
                                val data = json.getJSONArray("data")

                                val watchfaceArrayStack = arrayListOf<WatchfaceStack>()

                                for (d in 0 until data.length()) {
                                    val list = data.getJSONObject(d).getJSONArray("list")
                                    val watchfaceArray = arrayListOf<Watchface>()

                                    for (l in 0 until list.length()) {
                                        val url = URL(list.getJSONObject(l).getString("icon"))
                                        val preview = BitmapFactory.decodeStream(url.openConnection().getInputStream())

                                        watchfaceArray.add(
                                            Watchface(
                                                title = list.getJSONObject(l).getString("display_name"),
                                                preview = preview,
                                                url = list.getJSONObject(l).getString("config_file")
                                            )
                                        )
                                    }

                                    watchfaceArrayStack.add(
                                        WatchfaceStack(
                                            title = data.getJSONObject(d).getString("tab_name"),
                                            stack = watchfaceArray,
                                            hasCategories = a.hasCategories
                                        )
                                    )
                                }

                                arrayList.add(
                                    WatchfaceCommonStack(
                                        title = a.title,
                                        alias = a.alias,
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

                runOnUiThread {
                    findViewById<RecyclerView>(R.id.watchfaceCommonRecyclerView).let { RecyclerView ->
                        RecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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
            }.start()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}