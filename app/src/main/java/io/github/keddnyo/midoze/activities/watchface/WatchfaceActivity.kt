package io.github.keddnyo.midoze.activities.watchface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.watchface.WatchfaceAdapter
import io.github.keddnyo.midoze.local.dataModels.Watchface
import io.github.keddnyo.midoze.local.menu.Dimens
import io.github.keddnyo.midoze.utils.Display

class WatchfaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchface_stack)

        if (intent.hasExtra("device")) {
            val watchfaceDataStack: Watchface.WatchfaceDataStack =
                GsonBuilder().create().fromJson(
                    intent.getStringExtra("device"),
                    object : TypeToken<Watchface.WatchfaceDataStack>() {}.type
                )

            title = watchfaceDataStack.name

            findViewById<RecyclerView>(R.id.watchfaceRecyclerView).let { RecyclerView ->
                RecyclerView.layoutManager =
                    GridLayoutManager(
                        this, Display()
                            .getGridLayoutIndex(this, Dimens.CARD_GRID_WIDTH)
                    )

                WatchfaceAdapter().let { adapter ->
                    RecyclerView.adapter = adapter

                    adapter.addWatchfaceList(
                        watchfaceDataStack.watchfaceData
                    )
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}