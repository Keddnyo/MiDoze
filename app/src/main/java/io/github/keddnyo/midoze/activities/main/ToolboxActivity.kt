package io.github.keddnyo.midoze.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.ToolboxAdapter
import io.github.keddnyo.midoze.local.toolbox.ToolboxRepository

class ToolboxActivity : AppCompatActivity() {
    val context = this@ToolboxActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.menu_title)
        setContentView(R.layout.activity_toolbox)

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
}