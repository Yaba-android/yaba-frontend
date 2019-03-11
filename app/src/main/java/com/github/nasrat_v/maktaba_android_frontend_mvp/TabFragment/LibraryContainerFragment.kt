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
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.LibraryListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.AllBooks.AllBooksFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Download.DownloadFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.Groups.GroupsFragment

class LibraryContainerFragment : Fragment(),
    IDownloadBookClickCallback {

    private lateinit var mBookClickCallback: IBookClickCallback
    private lateinit var mGroupClickCallback: IGroupClickCallback
    private lateinit var mTabLayoutSetupCallback: ITabLayoutSetupCallback
    private lateinit var mLibraryDataset: LibraryListBModel
    private val mTabNamesList = arrayListOf<String>()
    private val mDownloadFrag = DownloadFragment()
    private val mGroupsFrag = GroupsFragment()
    private val mAllBooksFrag = AllBooksFragment()
    private val nbBookPerRow = 2
    private val nbGroupPerRow = 1

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
        initTabNameList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_container, container, false)
        val viewPager = view.findViewById(R.id.viewpager_container_fragment) as ViewPager

        initDataset(container!!)
        viewPager.adapter = ItemsPagerAdapter(childFragmentManager, mTabNamesList)
        mTabLayoutSetupCallback.setupTabLayout(viewPager)
        return view
    }

    override fun downloadBookEventButtonClicked(book: BModel) {
        val downloadBook = DownloadBModel(book)

        if (!addBookToRowWithSpace(mLibraryDataset.downloadBooks.last(), downloadBook)) {
            val newList = arrayListOf<DownloadBModel>()

            newList.add(downloadBook)
            mLibraryDataset.downloadBooks.add(DownloadListBModel(newList))
        }
    }

    private fun addBookToRowWithSpace(rowBooks: DownloadListBModel, newBook: DownloadBModel) : Boolean {
        if (rowBooks.bookModels.size < nbBookPerRow) {
            rowBooks.bookModels.add(newBook)
            return true
        }
        return false
    }

    fun setBookClickCallback(bookClickCallback: IBookClickCallback) {
        mBookClickCallback = bookClickCallback
    }

    fun setGroupClickCallback(groupClickCallback: IGroupClickCallback) {
        mGroupClickCallback = groupClickCallback
    }

    private fun initTabFragment() {
        mDownloadFrag.setBookClickCallback(mBookClickCallback)
        mGroupsFrag.setBookClickCallback(mBookClickCallback) // on set l'interface qui va permettre au fragment de renvoyer l'event click
        mGroupsFrag.setGroupClickCallback(mGroupClickCallback)
        mAllBooksFrag.setBookClickCallback(mBookClickCallback)
        mAllBooksFrag.setDownloadBookClickCallback(this)
    }

    private fun initTabNameList() {
        mTabNamesList.add("Download")
        mTabNamesList.add("Groups")
        mTabNamesList.add("All Books")
    }

    private fun initDataset(container: ViewGroup) {
        val allbooks = arrayListOf<NoTitleListBModel>()
        val downloads = arrayListOf<DownloadListBModel>()
        val groups = arrayListOf<GroupListBModel>()

        mockDatasetAllBooks(container, allbooks)
        mockDatasetGroups(allbooks, groups)
        mockDatasetDownload(allbooks, downloads)
        mLibraryDataset = LibraryListBModel(downloads, groups, allbooks)
    }

    private fun mockDatasetAllBooks(container: ViewGroup, dataset: ArrayList<NoTitleListBModel>) {
        val factory = BModelRandomProvider(container.context)

        for (index in 0..15) {
            dataset.add(
                NoTitleListBModel(
                    factory.getRandomsInstances(nbBookPerRow)
                )
            )
        }
    }

    private fun mockDatasetGroups(allbooksDataset: ArrayList<NoTitleListBModel>, dataset: ArrayList<GroupListBModel>) {
        dataset.addAll(LibraryListBModelProvider().getGroupListFromList(nbGroupPerRow, allbooksDataset))
    }

    private fun mockDatasetDownload(allbooksDataset: ArrayList<NoTitleListBModel>, dataset: ArrayList<DownloadListBModel>) {
        dataset.addAll(LibraryListBModelRandomProvider().getRandomDownloadedListBookFromList(3, nbBookPerRow, allbooksDataset))
    }

    internal inner class ItemsPagerAdapter(fm: FragmentManager, private var tabNames: ArrayList<String>)
        : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    mDownloadFrag.setDatasetVerticalRecyclerView(mLibraryDataset.downloadBooks)
                    return mDownloadFrag
                }
                1 ->  {
                    mGroupsFrag.setDatasetVerticalRecyclerView(mLibraryDataset.groupBooks)
                    return mGroupsFrag
                }
                2 ->  {
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