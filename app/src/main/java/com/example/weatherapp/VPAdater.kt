package com.example.weatherapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class VPAdater(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        when(p0){
            0 -> return Simple()
            1 -> return Detail()
        }
        return null

    }

    override fun getCount(): Int {
        return 2
    }
}