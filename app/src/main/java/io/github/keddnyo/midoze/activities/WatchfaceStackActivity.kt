package io.github.keddnyo.midoze.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.WatchfaceCommonAdapter
import io.github.keddnyo.midoze.local.dataModels.Watchface
import io.github.keddnyo.midoze.local.dataModels.WatchfaceCommonStack
import io.github.keddnyo.midoze.local.dataModels.WatchfaceStack
import io.github.keddnyo.midoze.local.devices.WatchfaceRepository.watchfaceStack
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.OnlineStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL

class WatchfaceStackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchface)

        val refreshWatchfaceLayout: SwipeRefreshLayout = findViewById(R.id.refreshWatchfaceLayout)

        if (OnlineStatus(this).isOnline) {
            Thread {
                runOnUiThread {
                    refreshWatchfaceLayout.isRefreshing = true
                }

                val watchfaceCommonStackArray = arrayListOf<WatchfaceCommonStack>()

                for (a in watchfaceStack) {
                    val content = runBlocking(Dispatchers.IO) {
                        Requests().getWatchfaceData(a)
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
                                stack = watchfaceArray
                            )
                        )
                    }

                    watchfaceCommonStackArray.add(
                        WatchfaceCommonStack(
                            title = a,
                            alias = a,
                            stack = watchfaceArrayStack
                        )
                    )

                    runOnUiThread {
                        findViewById<RecyclerView>(R.id.watchfaceRecyclerView).let { RecyclerView ->
                            RecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                            WatchfaceCommonAdapter().let { adapter ->
                                RecyclerView.adapter = adapter

                                adapter.addWatchfaceList(
                                    watchfaceCommonStackArray
                                )
                            }
                        }
                    }
                }
                runOnUiThread {
                    refreshWatchfaceLayout.isRefreshing = false
                }
            }.start()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}