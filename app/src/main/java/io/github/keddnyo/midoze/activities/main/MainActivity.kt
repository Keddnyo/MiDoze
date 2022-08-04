package io.github.keddnyo.midoze.activities.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.ToolboxAdapter
import io.github.keddnyo.midoze.local.toolbox.ToolboxRepository
import io.github.keddnyo.midoze.remote.Routes

class MainActivity : AppCompatActivity() {
    val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.menu_title)
        setContentView(R.layout.activity_main)

        val toolboxRecyclerView: RecyclerView = findViewById(R.id.toolboxRecyclerView)
        toolboxRecyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        val adapter = ToolboxAdapter()
        toolboxRecyclerView.adapter = adapter

        adapter.addItems(
            ToolboxRepository(context).items
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(Routes.GITHUB_APP_REPOSITORY))
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }
}