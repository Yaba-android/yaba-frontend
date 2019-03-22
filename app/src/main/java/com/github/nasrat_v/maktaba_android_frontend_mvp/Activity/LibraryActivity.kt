package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.folioreader.FolioReader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IDownloadBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IGroupClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.LibraryContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.TabLayoutCustomListener

/*
    for epub files see:

    gutenberg.org/ebooks/

 */

class LibraryActivity : AppCompatActivity(),
    IBookClickCallback,
    IGroupClickCallback,
    IDownloadBookClickCallback,
    ITabLayoutSetupCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mLibraryDataset: LibraryBModel
    private val mFolioReader = FolioReader.get()
    private val mContainerFragment = LibraryContainerFragment()

    companion object {
        const val DOWNLOAD_NB_BOOK_PER_ROW = 2
        const val ALLBOOKS_NB_BOOK_PER_ROW = 2
        const val DOWNLOAD_NB_BOOK_COLUMNS = 3
        const val ALLBOOKS_NB_BOOK_COLUMNS = 10
        const val GROUPS_NB_GROUP_PER_ROW = 1
        const val REQUEST_BOOKS_ADD_DOWNLOAD_LIST = 0
        const val ACTIVITY_NAME = "Library"
        const val BOOKS_ADD_DOWNLOAD_LIST = "BooksToAddToDownloadList"
        const val DOWNLOADED_BOOKS = "DownloadedBooks"
        const val PATH_TO_EBOOK_EPUB = "/storage/emulated/0/Download/pg58892-images.epub"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_library)

        mLibraryDataset = intent.getParcelableExtra(RecommendedActivity.LIBRARY_DATASET)
        setListenerButtonCloseProfile()
        setListenerBrowseButtonFooter()
        setListenerRecommendedButtonFooter()

        initRootDrawerLayout()
        if (savedInstanceState == null) {
            initFragmentManager()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val anim = intent!!.getIntExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 1)

        if (anim == 0) // left
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        else if (anim == 1) // right
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_BOOKS_ADD_DOWNLOAD_LIST) && resultCode == Activity.RESULT_OK) {
            val booksToAddToDownload =
                data!!.getParcelableArrayListExtra<BModel>(BOOKS_ADD_DOWNLOAD_LIST)

            addDownloadedBooks(booksToAddToDownload)
        }
    }

    override fun bookEventButtonClicked(book: BModel) {
        if (isBookAlreadyDownloaded(book)) {
            Toast.makeText(this, ("Opening " + book.title + " ..."), Toast.LENGTH_SHORT).show()
            openFolioReader()
        } else {
            Toast.makeText(this, ("Downloading " + book.title + " ..."), Toast.LENGTH_SHORT).show()
            addDownloadedBook(book)
        }
    }

    override fun groupEventButtonClicked(group: GroupBModel) {
        val intent = Intent(this, GroupActivity::class.java)

        intent.putExtra(GroupActivity.SELECTED_GROUP, group)
        intent.putExtra(DOWNLOADED_BOOKS, mLibraryDataset.downloadBooks)
        startActivityForResult(intent, REQUEST_BOOKS_ADD_DOWNLOAD_LIST)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun downloadBookEventButtonClicked(book: BModel) {
        addDownloadedBook(book)
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val customListener = TabLayoutCustomListener(this)

        tabLayout.setupWithViewPager(viewPager)
        customListener.setTabTextToBold(tabLayout, tabLayout.selectedTabPosition)
        customListener.setListenerTabLayout(tabLayout)
    }

    private fun setListenerButtonCloseProfile() {
        val nav = findViewById<NavigationView>(R.id.nav_view_profile)
        val header = nav.getHeaderView(0)
        val buttonCloseProfile = header.findViewById<Button>(R.id.button_close_profile)

        buttonCloseProfile.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setListenerRecommendedButtonFooter() {
        val button = findViewById<Button>(R.id.button_recommended_footer)

        button.setOnClickListener {
            Toast.makeText(this, RecommendedActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            returnToHome()
        }
    }

    private fun setListenerBrowseButtonFooter() {
        val intent = Intent(this, BrowseActivity::class.java)
        val buttonBrowse = findViewById<Button>(R.id.button_browse_footer)

        buttonBrowse.setOnClickListener {
            Toast.makeText(this, BrowseActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            intent.putExtra(RecommendedActivity.LIBRARY_DATASET, mLibraryDataset)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun isBookAlreadyDownloaded(book: BModel): Boolean {
        mLibraryDataset.downloadBooks.forEach { list ->
            if (list.bookModels.find { it.book == book } != null)
                return true
        }
        return false
    }

    private fun openFolioReader() {
        mFolioReader.openBook(PATH_TO_EBOOK_EPUB)
    }

    private fun addDownloadedBooks(books: ArrayList<BModel>) {
        books.forEach {
            addDownloadedBook(it)
        }
    }

    private fun addDownloadedBook(book: BModel) {
        val downloadBook = DownloadBModel(book)

        if (!addBookToRowWithSpace(mLibraryDataset.downloadBooks.last(), downloadBook)) {
            val newList = arrayListOf<DownloadBModel>()

            newList.add(downloadBook)
            mLibraryDataset.downloadBooks.add(DownloadListBModel(newList))
        }
        mContainerFragment.notifyDataSetChanged()
    }

    private fun addBookToRowWithSpace(rowBooks: DownloadListBModel, newBook: DownloadBModel)
            : Boolean {

        if (rowBooks.bookModels.size < DOWNLOAD_NB_BOOK_PER_ROW) {
            rowBooks.bookModels.add(newBook)
            return true
        }
        return false
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun initRootDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawerLayout = findViewById(R.id.drawer_library)
        val mDrawerToggle = ActionBarDrawerToggle(
            this, mDrawerLayout, toolbar,
            R.string.navigation_drawer_profile_open,
            R.string.navigation_drawer_profile_close
        )
        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.toolbarNavigationClickListener = View.OnClickListener {
            mDrawerLayout.openDrawer(GravityCompat.START)
        }
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_drawer)
        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)
    }

    private fun initFragmentManager() {
        val mFragmentManager = supportFragmentManager

        mContainerFragment.setBookClickCallback(this) // permet de gerer les click depuis le fragment
        mContainerFragment.setGroupClickCallback(this)
        mContainerFragment.setDownloadBookClickCallback(this)
        mContainerFragment.setLibraryDataset(mLibraryDataset)
        //mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.fragment_container_library, mContainerFragment).commit()
    }
}