package com.github.nasrat_v.yaba_demo.TabFragment

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.github.nasrat_v.yaba_demo.*
import com.github.nasrat_v.yaba_demo.Activity.BookDetailsActivity
import com.github.nasrat_v.yaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_demo.ICallback.IBookInfosProvider
import com.github.nasrat_v.yaba_demo.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.Model.BookDetailsBRModel
import com.github.nasrat_v.yaba_demo.Services.Factory.BookDetailsBRModelFactory
import com.github.nasrat_v.yaba_demo.TabFragment.Overview.OverviewFragment
import com.github.nasrat_v.yaba_demo.TabFragment.Review.ReviewFragment

class BookDetailsContainerFragment : androidx.fragment.app.Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mTabLayoutSetupCallback: ITabLayoutSetupCallback
    private lateinit var mBookInfosProvider: IBookInfosProvider
    private lateinit var mBookDetailsBRModel: BookDetailsBRModel
    private var mNumberRating = 0
    private val mTabNamesList = arrayListOf<String>()
    private val mReview = ReviewFragment()
    private val mOverview = OverviewFragment()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE

    companion object {
        const val RECYCLER_VIEW_NB_BOOKS_PER_ROW = 6
        const val RECYCLER_VIEW_NB_COLUMNS = 1
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
        val viewPager = view.findViewById(R.id.viewpager_container_fragment) as androidx.viewpager.widget.ViewPager

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

    fun setLanguageCode(languageCode: String) {
        mLanguage = languageCode
    }

    private fun initEmptyDataset() {
        mBookDetailsBRModel = BookDetailsBRModelFactory().getEmptyInstance()
    }

    private fun resetAllDatasetFragment() { // on reset tous les dataset une fois que le container les a fetch en async task
        mOverview.setBookVerticalRecyclerView(mBookDetailsBRModel.books)
        mOverview.initBookAttributes()
        mReview.setBookVerticalRecyclerView(mBookDetailsBRModel.books)
        mReview.setReviewVerticalRecyclerView(mBookDetailsBRModel.reviews)
        mReview.initBookAttributes()

        mOverview.notifyVerticalDataSetChanged()
        mReview.notifyVerticalBookDataSetChanged()
        mReview.notifyVerticalReviewDataSetChanged()
    }

    private fun initTabFragment() {
        mReview.setTabFragmentClickCallback(mBookClickCallback)
        mReview.setLanguageCode(mLanguage)
        mOverview.setTabFragmentClickCallback(mBookClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
        mOverview.setLanguageCode(mLanguage)
    }

    private fun iniTabNamesList() {
        mTabNamesList.add(getString(StringLocaleResolver(mLanguage).getRes(R.string.review_tab)) + " ($mNumberRating)")
        mTabNamesList.add(getString(StringLocaleResolver(mLanguage).getRes(R.string.overview_tab)))
    }

    internal inner class ItemsPagerAdapter(fm: androidx.fragment.app.FragmentManager, private var tabNames: ArrayList<String>) :
        androidx.fragment.app.FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): androidx.fragment.app.Fragment {
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
            return mReview
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