package io.github.keddnyo.midoze.adapters.menu

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.keddnyo.midoze.fragments.ApplicationFragment
import io.github.keddnyo.midoze.fragments.FirmwareFragment
import io.github.keddnyo.midoze.fragments.WatchfaceFragment
import io.github.keddnyo.midoze.local.repositories.ApplicationRepository

class MenuAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FirmwareFragment()
            1 -> WatchfaceFragment()
            2 -> ApplicationFragment()
            else -> FirmwareFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(ApplicationRepository.TITLE_ARRAY[position])
    }
}