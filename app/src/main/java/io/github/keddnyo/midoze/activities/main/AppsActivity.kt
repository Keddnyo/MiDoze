package io.github.keddnyo.midoze.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.AppsAdapter
import io.github.keddnyo.midoze.local.packages.PackageRepository

class AppsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)

        findViewById<RecyclerView>(R.id.appsRecyclerView).let { RecyclerView ->
            RecyclerView.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
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