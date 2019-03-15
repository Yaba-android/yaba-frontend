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
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.LibraryActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IDownloadBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IGroupClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.AllBooks.AllBooksFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Download.DownloadFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Groups.GroupsFragment

class LibraryContainerFragment : Fragment() {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mGroupClickCallback: IGroupClickCallback
    private lateinit var mDownloadBookClickCallback: IDownloadBookClickCallback
    private lateinit var mTabLayoutSetupCallback: ITabLayoutSetupCallback
    private lateinit var mLibraryDataset: LibraryBModel
    private val mTabNamesList = arrayListOf<String>()
    private val mDownloadFrag = DownloadFragment()
    private val mGroupsFrag = GroupsFragment()
    private val mAllBooksFrag = AllBooksFragment()

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

        initTabFragment()
        initTabNamesList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_container, container, false)
        val viewPager = view.findViewById(R.id.viewpager_container_fragment) as ViewPager

        viewPager.adapter = ItemsPagerAdapter(childFragmentManager, mTabNamesList)
        mTabLayoutSetupCallback.setupTabLayout(viewPager)
        return view
    }

    fun setBookClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setGroupClickCallback(groupClickCallback: IGroupClickCallback) {
        mGroupClickCallback = groupClickCallback
    }

    fun setDownloadBookClickCallback(downloadBookClickCallback: IDownloadBookClickCallback) {
        mDownloadBookClickCallback = downloadBookClickCallback
    }

    fun setLibraryDataset(libraryDataset: LibraryBModel) {
        mLibraryDataset = libraryDataset
    }

    fun notifyDataSetChangedDownloadList() {
        mDownloadFrag.notifyDataSetChangedDownloadList()
    }

    private fun initTabFragment() {
        mDownloadFrag.setBookClickCallback(mBookClickCallback)
        mGroupsFrag.setBookClickCallback(mBookClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
        mGroupsFrag.setGroupClickCallback(mGroupClickCallback)
        mAllBooksFrag.setBookClickCallback(mBookClickCallback)
        mAllBooksFrag.setDownloadBookClickCallback(mDownloadBookClickCallback)
    }

    private fun initTabNamesList() {
        mTabNamesList.add("Download")
        mTabNamesList.add("Groups")
        mTabNamesList.add("All Books")
    }

    internal inner class ItemsPagerAdapter(fm: FragmentManager, private var tabNames: ArrayList<String>) :
        FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    mDownloadFrag.setDatasetVerticalRecyclerView(mLibraryDataset.downloadBooks)
                    return mDownloadFrag
                }
                1 -> {
                    mGroupsFrag.setDatasetVerticalRecyclerView(mLibraryDataset.groupBooks)
                    return mGroupsFrag
                }
                2 -> {
                    mAllBooksFrag.setDatasetVerticalRecyclerView(mLibraryDataset.allBooks)
                    mAllBooksFrag.setDownloadedBooks(mLibraryDataset.downloadBooks)
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