package io.github.keddnyo.midoze.activities.watchface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.WatchfaceStackAdapter
import io.github.keddnyo.midoze.local.dataModels.WatchfaceStack

class WatchfaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchface_stack)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        if (intent.hasExtra("name")) {
            title = intent.getStringExtra("name")
            prefs.getString("WatchfaceStack", "").toString().let { WatchfaceCommonStackPreference ->
                if (WatchfaceCommonStackPreference.isNotEmpty()) {
                    val watchfaceArrayStack: ArrayList<WatchfaceStack> =
                        GsonBuilder().create().fromJson(
                            WatchfaceCommonStackPreference,
                            object : TypeToken<ArrayList<WatchfaceStack>>() {}.type
                        )

                    findViewById<RecyclerView>(R.id.watchfaceRecyclerView).let { RecyclerView ->
                        RecyclerView.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                        WatchfaceStackAdapter().let { adapter ->
                            RecyclerView.adapter = adapter

                            adapter.addWatchfaceList(
                                watchfaceArrayStack
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}