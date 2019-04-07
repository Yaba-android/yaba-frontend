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
import android.util.DisplayMetrics
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.LibraryActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IGroupClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Language.StringLocaleResolver
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book.LibraryBModelFactory
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.AllBooks.AllBooksFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Download.DownloadFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Groups.GroupsFragment
import java.io.Serializable

class LibraryContainerFragment : Fragment(),
    Serializable {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mGroupClickCallback: IGroupClickCallback
    private lateinit var mTabLayoutSetupCallback: ITabLayoutSetupCallback
    private lateinit var mLibraryDataset: LibraryBModel
    private lateinit var mDisplayMetrics: DisplayMetrics
    private val mTabNamesList = arrayListOf<String>()
    private val mDownloadFrag = DownloadFragment()
    private val mGroupsFrag = GroupsFragment()
    private val mAllBooksFrag = AllBooksFragment()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LibraryActivity) {
            // callback qui va permettre de recuperer le tablayout depuis sstore et de lui setter le viewpager qui est dans fragment_container
            // ceci regle le probleme de la nav view  qui passe sour la toolbar
            mTabLayoutSetupCallback = context
        } else {
            throw ClassCastException("$context must implement TabLayoutSetupCallback")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEmptyLibraryDataset()
        initTabNamesList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_container, container, false)
        val viewPager = view.findViewById(R.id.viewpager_container_fragment) as ViewPager

        viewPager.adapter = ItemsPagerAdapter(childFragmentManager, mTabNamesList)
        mTabLayoutSetupCallback.setupTabLayout(viewPager)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initTabFragment()
    }

    fun setBookClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setGroupClickCallback(groupClickCallback: IGroupClickCallback) {
        mGroupClickCallback = groupClickCallback
    }

    fun setLibraryDataset(libraryDataset: LibraryBModel) {
        mLibraryDataset = libraryDataset
        resetAllDatasetFragment()
    }

    fun notifyDownloadDataSetChanged() {
        mDownloadFrag.setDatasetVerticalRecyclerView(mLibraryDataset.downloadBooks)
        mDownloadFrag.notifyHorizontalDataSetChanged()
        mAllBooksFrag.setDownloadedBooks(mLibraryDataset.downloadBooks)
        mAllBooksFrag.notifyBothDataSetChanged()
    }

    fun setDisplayMetrics(displayMetrics: DisplayMetrics) {
        mDisplayMetrics = displayMetrics
    }

    fun setLanguageCode(languageCode: String) {
        mLanguage = languageCode
    }

    private fun initEmptyLibraryDataset() { // on init une library empty en attendant les dataset de l'asynTask
        mLibraryDataset = LibraryBModelFactory().getEmptyInstance()
    }

    private fun resetAllDatasetFragment() { // on reset les dataset une fois que l'asyncTask les as chargés
        mDownloadFrag.setDatasetVerticalRecyclerView(mLibraryDataset.downloadBooks)
        mGroupsFrag.setDatasetVerticalRecyclerView(mLibraryDataset.groupBooks)
        mAllBooksFrag.setDatasetVerticalRecyclerView(mLibraryDataset.allBooks)
        mAllBooksFrag.setDownloadedBooks(mLibraryDataset.downloadBooks)

        // on ne notifie que les deux premiers tabs car le troisieme n'est pas encore inflate
        mDownloadFrag.notifyVerticalDataSetChanged()
        mGroupsFrag.notifyVerticalDataSetChanged()
    }

    private fun initTabFragment() {
        mDownloadFrag.setBookClickCallback(mBookClickCallback)
        mDownloadFrag.setDisplayMetrics(mDisplayMetrics)
        mDownloadFrag.setLanguageCode(mLanguage)
        mGroupsFrag.setBookClickCallback(mBookClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
        mGroupsFrag.setGroupClickCallback(mGroupClickCallback)
        mGroupsFrag.setLanguageCode(mLanguage)
        mAllBooksFrag.setBookClickCallback(mBookClickCallback)
        mAllBooksFrag.setDisplayMetrics(mDisplayMetrics)
        mAllBooksFrag.setLanguageCode(mLanguage)
    }

    private fun initTabNamesList() {
        mTabNamesList.clear()
        mTabNamesList.add(getString(StringLocaleResolver(mLanguage).getRes(R.string.download_tab)))
        mTabNamesList.add(getString(StringLocaleResolver(mLanguage).getRes(R.string.groups_tab)))
        mTabNamesList.add(getString(StringLocaleResolver(mLanguage).getRes(R.string.allbooks_tab)))
    }

    internal inner class ItemsPagerAdapter(fm: FragmentManager, private var tabNames: ArrayList<String>) :
        FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    return mDownloadFrag
                }
                1 -> {
                    return mGroupsFrag
                }
                2 -> {
                    return mAllBooksFrag
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