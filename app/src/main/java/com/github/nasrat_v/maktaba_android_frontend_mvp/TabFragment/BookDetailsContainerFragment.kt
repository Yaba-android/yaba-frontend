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
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookInfosProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Overview.OverviewFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Review.ReviewFragment

class BookDetailsContainerFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mTabLayoutSetupCallback: ITabLayoutSetupCallback
    private lateinit var mBookInfosProvider: IBookInfosProvider
    private lateinit var mAllBooksFromDatabase: ArrayList<BModel>
    private var mNumberRating = 0
    private val mTabNamesList = arrayListOf<String>()
    private val mReview = ReviewFragment()
    val mOverview = OverviewFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BookDetailsActivity) {
            // callback qui va permettre de recuperer le tablayout depuis bookdetails et de lui setter le viewpager qui est dans fragment_container
            // ceci regle le probleme de la nav view  qui passe sour la toolbar
            mTabLayoutSetupCallback = context
        } else {
            throw ClassCastException("$context must implement TabLayoutSetupCallback")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTabFragment()
        iniTabNamesList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_container, container, false)
        val viewPager = view.findViewById(R.id.viewpager_container_fragment) as ViewPager

        viewPager.adapter = ItemsPagerAdapter(childFragmentManager, mTabNamesList)
        mTabLayoutSetupCallback.setupTabLayout(viewPager)
        return view
    }

    fun setTabFragmentClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setNumberRatingTabNameReview(numberRating: Int) {
        mNumberRating = numberRating
    }

    fun setBookInfosProvider(bookInfosProvider: IBookInfosProvider) {
        mBookInfosProvider = bookInfosProvider
    }

    fun setAllBooksFromDatabase(allBooksDb: ArrayList<BModel>) {
        mAllBooksFromDatabase = allBooksDb
    }

    private fun initTabFragment() {
        mReview.setTabFragmentClickCallback(mBookClickCallback)
        mOverview.setTabFragmentClickCallback(mBookClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
    }

    private fun iniTabNamesList() {
        mTabNamesList.add("Reviews ($mNumberRating)")
        mTabNamesList.add("Overview")
    }

    internal inner class ItemsPagerAdapter(fm: FragmentManager, private var tabNames: ArrayList<String>) :
        FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    mReview.setAllBooksFromDatabase(mAllBooksFromDatabase)
                    mReview.setSelectedBook(mBookInfosProvider.getSelectedBook())
                    return mReview
                }
                1 -> {
                    mReview.setAllBooksFromDatabase(mAllBooksFromDatabase)
                    mOverview.setSelectedBook(mBookInfosProvider.getSelectedBook())
                    return mOverview
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