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
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BookDetailsBRModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Review.Vertical.RModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.BookDetailsBRModelFactory
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Overview.OverviewFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Review.ReviewFragment

class BookDetailsContainerFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mTabLayoutSetupCallback: ITabLayoutSetupCallback
    private lateinit var mBookInfosProvider: IBookInfosProvider
    private lateinit var mBookDetailsBRModel: BookDetailsBRModel
    private var mNumberRating = 0
    private val mTabNamesList = arrayListOf<String>()
    private val mReview = ReviewFragment()
    private val mOverview = OverviewFragment()

    companion object {
        const val RECYCLER_VIEW_NB_BOOKS_PER_ROW = 6
        const val RECYCLER_VIEW_NB_COLUMNS = 1
        const val RECYCLER_VIEW_TITLE = "More Books from this Authors"
    }

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

        initEmptyDataset()
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

    fun setBookDetailBRModelDataset(dataset: BookDetailsBRModel) {
        mBookDetailsBRModel = dataset
        resetAllDatasetFragment()
    }

    private fun initEmptyDataset() {
        mBookDetailsBRModel = BookDetailsBRModelFactory().getEmptyInstance()
    }

    private fun resetAllDatasetFragment() { // on reset vetical book dataset une fois que le container les a fetch
        mOverview.setBookVerticalRecyclerView(mBookDetailsBRModel.books)
        mReview.setBookVerticalRecyclerView(mBookDetailsBRModel.books)
        mReview.setReviewVerticalRecyclerView(mBookDetailsBRModel.reviews)

        mOverview.notifyVerticalDataSetChanged()
        mReview.notifyVerticalBookDataSetChanged()
        mReview.notifyVerticalReviewDataSetChanged()
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
                    mReview.setSelectedBook(mBookInfosProvider.getSelectedBook())
                    return mReview
                }
                1 -> {
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