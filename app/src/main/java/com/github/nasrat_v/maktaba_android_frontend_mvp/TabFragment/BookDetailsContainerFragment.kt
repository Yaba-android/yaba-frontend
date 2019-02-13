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
import com.github.nasrat_v.maktaba_android_frontend_mvp.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.BookDetailsActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Overview.OverviewFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Review.ReviewFragment

class BookDetailsContainerFragment : Fragment() {

    private lateinit var mTabFragmentClickCallback: ITabFragmentClickCallback
    private lateinit var mTabLayoutSetupCallback: ITabLayoutSetupCallback
    private val mTabNamesList = arrayListOf<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BookDetailsActivity) {
            // callback qui va permettre de recuperer le tablayout depuis activity_main et de lui setter le viewpager qui est dans fragment_container
            // ceci regle le probleme de la nav view  qui passe sour la toolbar
            mTabLayoutSetupCallback = context as ITabLayoutSetupCallback
        } else {
            throw ClassCastException("$context must implement TabLayoutSetupCallback")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTabNamesList.add("Reviews (188)")
        mTabNamesList.add("Overview")
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

    internal inner class ItemsPagerAdapter(fm: FragmentManager, private var tabNames: ArrayList<String>)
        : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    val review = ReviewFragment()
                    review.setTabFragmentClickCallback(mTabFragmentClickCallback)
                    return review
                }
                1 ->  {
                    val overview = OverviewFragment()
                    overview.setTabFragmentClickCallback(mTabFragmentClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
                    return overview
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
}