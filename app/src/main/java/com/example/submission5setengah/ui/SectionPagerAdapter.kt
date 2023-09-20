package com.example.submission5setengah.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission5setengah.ui.detail.FollowFragment

class SectionPagerAdapter internal constructor(activity: AppCompatActivity, private val valueData: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {

        val fragment = FollowFragment()

        fragment.arguments = Bundle().apply {
            putString(FollowFragment.USERNAME, valueData)
            putInt(FollowFragment.PARAMETER, position)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}