package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TabFragment : Fragment() {

    lateinit var mClickInterface: ClickInterface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val x = inflater.inflate(R.layout.tab_layout, null)

        tabLayout = x.findViewById<View>(R.id.tabs) as TabLayout
        viewPager = x.findViewById<View>(R.id.viewpager) as ViewPager

        viewPager.adapter = MyAdapter(childFragmentManager)
        tabLayout.post { tabLayout.setupWithViewPager(viewPager) }

        return x
    }

    fun setClickInterface(cInterface: ClickInterface) {
        mClickInterface = cInterface
    }

    internal inner class MyAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return RecommendedTabbedFragment()
                1 ->  {
                    val browse = BrowseTabbedFragment()
                    browse.setClickInterface(mClickInterface) // on set l'interface qui va permettre au fragment de renvoyer l'event click
                    return browse
                }
            }
            return null
        }

        override fun getCount(): Int {
            return int_items
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Recommended"
                1 -> return "Browse"
            }
            return null
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var tabLayout: TabLayout
        @SuppressLint("StaticFieldLeak")
        lateinit var viewPager: ViewPager
        var int_items = 2
    }
}