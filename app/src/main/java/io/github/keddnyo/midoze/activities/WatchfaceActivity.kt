package io.github.keddnyo.midoze.activities

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.WatchfaceAdapter
import io.github.keddnyo.midoze.local.dataModels.Watchface
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL

class WatchfaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchfaces)

        Thread {
            val watchfaceArray = arrayListOf<Watchface>()

            val content = runBlocking(Dispatchers.IO) {
                Requests().getWatchfaceData()
            }

            val json = JSONObject(content)
            val data = json.getJSONArray("data")

            for (d in 0 until data.length()) {
                val list = data.getJSONObject(d).getJSONArray("list")

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
            }

            runOnUiThread {
                findViewById<RecyclerView>(R.id.watchfaceRecyclerView).let { RecyclerView ->
                    RecyclerView.layoutManager = GridLayoutManager(
                        this@WatchfaceActivity, Display()
                            .getGridLayoutIndex(this@WatchfaceActivity, 200)
                    )

                    WatchfaceAdapter().let { adapter ->
                        RecyclerView.adapter = adapter

                        adapter.addWatchfaceList(
                            watchfaceArray
                        )
                    }
                }
            }
        }.start()
    }
}