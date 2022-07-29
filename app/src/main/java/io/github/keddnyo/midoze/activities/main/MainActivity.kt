package io.github.keddnyo.midoze.activities.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.fragments.DeviceListFragment
import io.github.keddnyo.midoze.fragments.DeviceStackFragment
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Updates
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.AppVersion
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Updates(this@MainActivity).execute()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.deviceListFrame, DeviceStackFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_request -> {
                startActivity(Intent(this@MainActivity, RequestActivity::class.java))
            }
            R.id.action_about -> {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_APP_REPOSITORY))
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.findFragmentByTag("deviceListFragment")?.isVisible == true) {
            supportFragmentManager
                .popBackStack()

            supportFragmentManager
                .beginTransaction()
                .show(DeviceStackFragment())
                .commit()
        } else {
            onBackPressed()
        }
        return true
    }
}