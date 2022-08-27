package io.github.keddnyo.midoze.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.WatchfaceStackAdapter
import io.github.keddnyo.midoze.local.dataModels.Watchface
import io.github.keddnyo.midoze.local.dataModels.WatchfaceStack
import io.github.keddnyo.midoze.remote.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL

class WatchfaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchfaces)

        Thread {
            val watchfaceArrayStack = arrayListOf<WatchfaceStack>()

            val content = runBlocking(Dispatchers.IO) {
                Requests().getWatchfaceData()
            }

            val json = JSONObject(content)
            val data = json.getJSONArray("data")

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
                            url = URL(list.getJSONObject(l).getString("config_file"))
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

            runOnUiThread {
                findViewById<RecyclerView>(R.id.watchfaceRecyclerView).let { RecyclerView ->
                    RecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                    WatchfaceStackAdapter().let { adapter ->
                        RecyclerView.adapter = adapter

                        adapter.addWatchfaceList(
                            watchfaceArrayStack
                        )
                    }
                }
            }
        }.start()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}