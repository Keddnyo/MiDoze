package io.github.keddnyo.midoze.activities.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.menu.AppsAdapter
import io.github.keddnyo.midoze.local.packages.PackageRepository
import io.github.keddnyo.midoze.utils.Display

class ApplicationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        title = getString(R.string.menu_apps)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)

        findViewById<RecyclerView>(R.id.appsRecyclerView).let { RecyclerView ->
            RecyclerView.layoutManager = GridLayoutManager(
                this, Display()
                    .getGridLayoutIndex(this, 400)
            )

            AppsAdapter().let { adapter ->
                RecyclerView.adapter = adapter

                adapter.addItems(
                    PackageRepository().packages
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}