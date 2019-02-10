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

class MainContainerFragment : Fragment() {

    private lateinit var mClickCallback: ClickCallback
    private lateinit var mToolbarSetupCallback: TabLayoutSetupCallback
    private val mTabNamesList = arrayListOf<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) {
            // callback qui va permettre de recuperer le tablayout depuis activity_main et de lui setter le viewpager qui est dans fragment_container
            // ceci regle le probleme de la nav view  qui passe sour la toolbar
            mToolbarSetupCallback = context
        } else {
            throw ClassCastException("$context must implement TabLayoutSetupCallback")
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

    fun setClickCallback(clickCallback: MainContainerFragment.ClickCallback) {
        mClickCallback = clickCallback
    }

    internal inner class ItemsPagerAdapter(fm: FragmentManager, private var tabNames: ArrayList<String>)
        : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    val recommended = RecommendedFragment()
                    recommended.setClickCallback(mClickCallback)
                    return recommended
                }
                1 ->  {
                    val browse = BrowseFragment()
                    browse.setClickCallback(mClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
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

    interface ClickCallback {
        fun genreNavigationEventButtonClicked()
        fun bookEventButtonClicked()
    }
}