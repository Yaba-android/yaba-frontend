package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.MainActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Browse.BrowseFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Recommended.RecommendedFragment

class MainContainerFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private lateinit var mTabLayoutSetupCallback: ITabLayoutSetupCallback
    private lateinit var mAdditionalClickCallback: AdditionalClickCallback
    private val mTabNamesList = arrayListOf<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) {
            // callback qui va permettre de recuperer le tablayout depuis activity_main et de lui setter le viewpager qui est dans fragment_container
            // ceci regle le probleme de la nav view  qui passe sour la toolbar
            mTabLayoutSetupCallback = context as ITabLayoutSetupCallback
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
        mTabLayoutSetupCallback.setupTabLayout(viewPager)
        return view
    }

    fun setTabFragmentClickCallback(tabFragmentClickCallback: ITabFragmentClickCallback) {
        mTabFragmentClickCallback = tabFragmentClickCallback
    }

    fun setAdditionalClickCallback(additionalClickCallback: AdditionalClickCallback) {
        mAdditionalClickCallback = additionalClickCallback
    }

    internal inner class ItemsPagerAdapter(fm: FragmentManager, private var tabNames: ArrayList<String>)
        : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    val recommended =
                        RecommendedFragment()
                    recommended.setTabFragmentClickCallback(mTabFragmentClickCallback)
                    return recommended
                }
                1 ->  {
                    val browse =
                        BrowseFragment()
                    browse.setTabFragmentClickCallback(mTabFragmentClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
                    browse.setAdditionalClickCallback(mAdditionalClickCallback)
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

    interface AdditionalClickCallback {
        fun genreNavigationEventButtonClicked()
    }
}