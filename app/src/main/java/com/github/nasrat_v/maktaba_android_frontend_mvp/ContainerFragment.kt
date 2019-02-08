package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.support.v4.app.FragmentStatePagerAdapter

class ContainerFragment : Fragment() {

    lateinit var mClickCallback: GenreNavigationClickCallback
    lateinit var mToolbarSetupCallback: TabLayoutSetupCallback
    val mTabNamesList = arrayListOf<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) {
            mToolbarSetupCallback = context as TabLayoutSetupCallback
        } else {
            throw ClassCastException(context.toString() + " must implement TabLayoutSetupCallback")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTabNamesList.add("Recommended")
        mTabNamesList.add("Browse")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_container, container, false)

        val viewPager = view.findViewById(R.id.viewpager_container_fragment) as ViewPager
        viewPager.adapter = ItemsPagerAdapter(childFragmentManager, mTabNamesList)
        mToolbarSetupCallback.setupTabLayout(viewPager)

        return view
    }

    fun setGenreNavigationClickCallback(clickCallback: ContainerFragment.GenreNavigationClickCallback) {
        mClickCallback = clickCallback
    }

    internal inner class ItemsPagerAdapter(fm: FragmentManager, var tabNames: ArrayList<String>) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return RecommendedFragment()
                1 ->  {
                    val browse = BrowseFragment()
                    browse.setGenreNavigationClickCallback(mClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
                    return browse
                }
            }
            return null
        }

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }

        override fun getCount(): Int {
            return tabNames.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabNames[position]
        }
    }

    interface TabLayoutSetupCallback {
        fun setupTabLayout(viewPager: ViewPager)
    }

    interface GenreNavigationClickCallback {
        fun eventButtonClicked()
    }
}