package com.siekang.ui.quiz

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuizViewPagerAdapter(
    val activity: FragmentActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}