package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.folioreader.FolioReader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IGroupClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask.LibraryBModelAsyncFetchData
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.LibraryContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.TabLayoutCustomListener

/*
    for epub files see:
    gutenberg.org/ebooks/

 */

class LibraryActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<LibraryBModel>,
    IBookClickCallback,
    IGroupClickCallback,
    ITabLayoutSetupCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mLibraryDataset: LibraryBModel
    private lateinit var mAllBooksFromDatabase: ArrayList<BModel>
    private lateinit var mFolioReader: FolioReader
    private lateinit var mToolbar: Toolbar
    private lateinit var mTabLayout: TabLayout
    private lateinit var mContainerFragment: LibraryContainerFragment
    private var mFirstInit = true

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
        const val PATH_TO_EBOOK_EPUB = R.raw.jekyll_and_hyde
        const val ACTION_BUTTON_TEXT_DOWNLOAD = "Download Book"
        const val ACTION_BUTTON_TEXT_OPEN = "Open Book"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_library)

        mFirstInit = true
        mContainerFragment = LibraryContainerFragment()
        mDrawerLayout = findViewById(R.id.drawer_library)
        mToolbar = findViewById(R.id.toolbar_application)
        mTabLayout = findViewById(R.id.tabs)

        initRootDrawerLayout()
        initFragmentManager()
    }

    override fun onStart() {
        super.onStart()

        if (mFirstInit) {
            fetchAllBooksFromDatabase()
            mFolioReader = FolioReader.get()

            setListenerButtonCloseProfile()
            setListenerBrowseButtonFooter()
            setListenerRecommendedButtonFooter()
            supportLoaderManager.initLoader(0, null, this).forceLoad() // init library in async task
        }
        mFirstInit = false
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<LibraryBModel> {
        return LibraryBModelAsyncFetchData(
            this,
            mAllBooksFromDatabase
        )
    }

    override fun onLoadFinished(p0: Loader<LibraryBModel>, data: LibraryBModel?) {
        mLibraryDataset = data!!
        mContainerFragment.setLibraryDataset(mLibraryDataset)
    }

    override fun onLoaderReset(p0: Loader<LibraryBModel>) {
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
            requestOpenBook(book)
        } else {
            requestDownloadBook(book)
        }
    }

    override fun groupEventButtonClicked(group: GroupBModel) {
        val intent = Intent(this, GroupActivity::class.java)

        intent.putExtra(GroupActivity.SELECTED_GROUP, group)
        intent.putExtra(DOWNLOADED_BOOKS, mLibraryDataset.downloadBooks)
        startActivityForResult(intent, REQUEST_BOOKS_ADD_DOWNLOAD_LIST)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val customListener = TabLayoutCustomListener(this)

        mTabLayout.setupWithViewPager(viewPager)
        customListener.setTabTextToBold(mTabLayout, mTabLayout.selectedTabPosition)
        customListener.setListenerTabLayout(mTabLayout)
    }

    private fun fetchAllBooksFromDatabase() {
        mAllBooksFromDatabase = BModelProvider(this).getAllBooksFromDatabase()
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
        val buttonRecommended = findViewById<Button>(R.id.button_recommended_footer)

        buttonRecommended.setOnClickListener {
            Toast.makeText(this, RecommendedActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            returnToHome()
        }
    }

    private fun setListenerBrowseButtonFooter() {
        val intent = Intent(this, BrowseActivity::class.java)
        val buttonBrowse = findViewById<Button>(R.id.button_browse_footer)

        buttonBrowse.setOnClickListener {
            Toast.makeText(this, BrowseActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun requestDownloadBook(book: BModel) {
        val dialog = Dialog(this, R.style.DownloadOpenDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_action_book)

        val closeButton = dialog.findViewById<Button>(R.id.button_close_dialog)
        val actionButton = dialog.findViewById<Button>(R.id.button_action_dialog)

        actionButton.text = ACTION_BUTTON_TEXT_DOWNLOAD
        closeButton.setOnClickListener {
            dialog.hide()
        }
        actionButton.setOnClickListener {
            downloadBook(book)
            dialog.hide()
        }
        dialog.show()
    }

    private fun requestOpenBook(book: BModel) {
        val dialog = Dialog(this, R.style.DownloadOpenDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_action_book)

        val closeButton = dialog.findViewById<Button>(R.id.button_close_dialog)
        val actionButton = dialog.findViewById<Button>(R.id.button_action_dialog)

        actionButton.text = ACTION_BUTTON_TEXT_OPEN
        closeButton.setOnClickListener {
            dialog.hide()
        }
        actionButton.setOnClickListener {
            Toast.makeText(this, ("Opening " + book.title + " ..."), Toast.LENGTH_SHORT).show()
            dialog.hide()
            openBook(book)
        }
        dialog.show()
    }

    private fun downloadBook(book: BModel) {
        Toast.makeText(this, ("Downloading " + book.title + " ..."), Toast.LENGTH_SHORT).show()
        addDownloadedBook(book)
    }

    private fun openBook(book: BModel) {
        openFolioReader()
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
        mContainerFragment.notifyDownloadDataSetChanged()
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
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mDrawerToggle = ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar,
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
        val mFragmentTransaction = mFragmentManager.beginTransaction()

        mContainerFragment.setBookClickCallback(this) // permet de gerer les click depuis le fragment
        mContainerFragment.setGroupClickCallback(this)
        mFragmentTransaction.replace(R.id.fragment_container_library, mContainerFragment).commit()
    }
}