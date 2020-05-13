package com.github.nasrat_v.yaba_android.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import com.folioreader.Config
import com.folioreader.FolioReader
import com.github.nasrat_v.yaba_android.AsyncHydrater.GroupNoTitleListBModelAsyncHydrater
import com.github.nasrat_v.yaba_android.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_android.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListAdapter.GroupListBRecyclerViewAdapter
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_android.Listable.BottomOffsetDecoration
import com.github.nasrat_v.yaba_android.R
import com.github.nasrat_v.yaba_android.Services.Provider.Book.EBookProvider

@SuppressLint("Registered")
class GroupActivity : AppCompatActivity(),
    androidx.loader.app.LoaderManager.LoaderCallbacks<ArrayList<NoTitleListBModel>>,
    IBookClickCallback {

    companion object {
        const val GROUP_NB_BOOK_PER_ROW = 2
        const val SELECTED_GROUP = "SelectedGroup"
    }

    private lateinit var mSelectedGroup: GroupBModel
    private lateinit var mDownloadedBooks: ArrayList<DownloadListBModel>
    private lateinit var mAdapterBookVertical: GroupListBRecyclerViewAdapter
    private lateinit var mDrawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var mFolioReader: FolioReader
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mDisplayMetrics: DisplayMetrics
    private lateinit var mBookToDownload: BModel
    private val mDataset = arrayListOf<NoTitleListBModel>()
    private var mBooksToAddToDownload = arrayListOf<BModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    private var mFirstInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_group_structure)

        localeOnNewIntent()

        mSelectedGroup = intent.getParcelableExtra(SELECTED_GROUP)
        mDownloadedBooks = intent.getParcelableArrayListExtra(LibraryActivity.DOWNLOADED_BOOKS)
        mProgressBar = findViewById(R.id.progress_bar_group)
        mFirstInit = true

        initDisplayMetrics()
        initRootDrawerLayout()
        initVerticalRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        if (mFirstInit) {
            initFolioReader()

            setListenerBrowseButtonFooter()
            setListenerLibraryButtonFooter()
            setListenerRecommendedButtonFooter()

            supportLoaderManager.initLoader(0, null, this).forceLoad() // init NoTitleListBModel in async task
        }
        mFirstInit = false
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): androidx.loader.content.Loader<ArrayList<NoTitleListBModel>> {
        return GroupNoTitleListBModelAsyncHydrater(this, mSelectedGroup)
    }

    override fun onLoadFinished(p0: androidx.loader.content.Loader<ArrayList<NoTitleListBModel>>, data: ArrayList<NoTitleListBModel>?) {
        mDataset.clear()
        mDataset.addAll(data!!)
        setGroupDetailsAttributes()
        mAdapterBookVertical.notifyDataSetChanged()
        mProgressBar.visibility = View.GONE
    }

    override fun onLoaderReset(p0: androidx.loader.content.Loader<ArrayList<NoTitleListBModel>>) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_back_arrow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_back) {
            finishSendResult()
        }
        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun bookEventButtonClicked(book: BModel) {
        if (isBookAlreadyDownloaded(book)) {
            requestOpenBook(book)
        } else {
            requestDownloadBook(book)
        }
    }

    private fun setListenerRecommendedButtonFooter() {
        val button = findViewById<Button>(R.id.button_recommended_footer)

        button.setOnClickListener {
            returnToHome()
        }
    }

    private fun setListenerBrowseButtonFooter() {
        val intent = Intent(this, BrowseActivity::class.java)
        val button = findViewById<Button>(R.id.button_browse_footer)

        button.setOnClickListener {
            startNewActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finishSendResult()
        }
    }

    private fun setListenerLibraryButtonFooter() {
        val intent = Intent(this, LibraryActivity::class.java)
        val button = findViewById<Button>(R.id.button_library_footer)

        button.setOnClickListener {
            intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)
            startNewActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finishSendResult()
        }
    }

    private fun finishSendResult() {
        if (!mBooksToAddToDownload.isEmpty()) {
            val intent = Intent()

            intent.putExtra(LibraryActivity.BOOKS_ADD_DOWNLOAD_LIST, mBooksToAddToDownload)
            intent.putExtra(StringLocaleResolver.LANGUAGE_CODE, mLanguage)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
        mBooksToAddToDownload.clear()
    }

    private fun startNewActivity(intent: Intent) {
        intent.putExtra(StringLocaleResolver.LANGUAGE_CODE, mLanguage)
        startActivity(intent)
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)
        startNewActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finishSendResult()
    }

    private fun localeOnNewIntent() {
        mLanguage =
            intent.getStringExtra(StringLocaleResolver.LANGUAGE_CODE) ?: StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    }

    private fun requestDownloadBook(book: BModel) {
        val dialog = Dialog(this, R.style.DownloadOpenDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_action_book)

        val closeButton = dialog.findViewById<Button>(R.id.button_close_dialog)
        val actionButton = dialog.findViewById<Button>(R.id.button_action_dialog)
        val title = dialog.findViewById<TextView>(R.id.title_book_dialog)
        val author = dialog.findViewById<TextView>(R.id.author_book_dialog)

        author.text = book.author.name
        title.text = book.title
        actionButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.download))
        closeButton.setOnClickListener {
            dialog.hide()
        }
        actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.download_book_dialog, 0, 0, 0) // left
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
        val title = dialog.findViewById<TextView>(R.id.title_book_dialog)
        val author = dialog.findViewById<TextView>(R.id.author_book_dialog)

        author.text = book.author.name
        title.text = book.title
        actionButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.open_it))
        closeButton.setOnClickListener {
            dialog.hide()
        }
        actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.left_arrow, 0, 0, 0) // left
        actionButton.setOnClickListener {
            Toast.makeText(
                this,
                (getString(StringLocaleResolver(mLanguage).getRes(R.string.opening)) + ' ' + book.title + " ..."),
                Toast.LENGTH_SHORT
            ).show()
            dialog.hide()
            openBook(book.filePath)
        }
        dialog.show()
    }

    private fun downloadBook(book: BModel) {
        mBookToDownload = book

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                LibraryActivity.STORAGE_PERMISSION_CODE
            )
        } else {
            startDownloading()
        }
        addDownloadedBook(book)
        mBooksToAddToDownload.add(book)
        mAdapterBookVertical.notifyDataSetChanged()
    }

    private fun startDownloading() {
        Toast.makeText(
            this,
            (getString(StringLocaleResolver(mLanguage).getRes(R.string.downloading)) + ' ' + mBookToDownload.title + " ..."),
            Toast.LENGTH_SHORT
        ).show()
        EBookProvider(this, mBookToDownload.filePath).startDownloading()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LibraryActivity.STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownloading()
                } else {
                    Toast.makeText(
                        this,
                        (getString(StringLocaleResolver(mLanguage).getRes(R.string.error_permission_download))),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun openBook(bookPath: String) {
        openFolioReader(bookPath)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun isBookAlreadyDownloaded(book: BModel): Boolean {
        mDownloadedBooks.forEach { list ->
            if (list.bookModels.find { it.book.remoteId == book.remoteId } != null)
                return true
        }
        return false
    }

    private fun openFolioReader(bookPath: String) {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()

        mFolioReader.openBook("$path/$bookPath")
    }

    private fun addDownloadedBook(book: BModel) {
        val downloadBook = DownloadBModel(book)

        if (!addBookToRowWithSpace(mDownloadedBooks.last(), downloadBook)) {
            val newList = arrayListOf<DownloadBModel>()

            newList.add(downloadBook)
            mDownloadedBooks.add(DownloadListBModel(newList))
        }
    }

    private fun addBookToRowWithSpace(rowBooks: DownloadListBModel, newBook: DownloadBModel)
            : Boolean {

        if (rowBooks.bookModels.size < GROUP_NB_BOOK_PER_ROW) {
            rowBooks.bookModels.add(newBook)
            return true
        }
        return false
    }

    private fun setGroupDetailsAttributes() {
        val title = findViewById<TextView>(R.id.toolbar_title)

        title.text = mSelectedGroup.genre.name
    }

    private fun initFolioReader() {
        val config = Config()
            .setDirection(Config.Direction.VERTICAL)
            .setFont(R.font.noto_sans_family)
            .setThemeColorRes(R.color.colorAppBlueHolo)

        mFolioReader = FolioReader.get().setConfig(config, true)
    }

    private fun initRootDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawerLayout = findViewById(R.id.drawer_group)
        val mDrawerToggle = ActionBarDrawerToggle(
            this, mDrawerLayout, toolbar,
            R.string.navigation_drawer_profile_open,
            R.string.navigation_drawer_profile_close
        )

        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.syncState()
        mDrawerLayout.addDrawerListener(mDrawerToggle)
    }

    private fun initVerticalRecyclerView() {
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val verticalRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.vertical_double_recyclerview)
        val sortButton = findViewById<Button>(R.id.sort_button)

        mAdapterBookVertical =
            GroupListBRecyclerViewAdapter(
                this,
                mDataset,
                mDownloadedBooks,
                this
            )
        sortButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.sort))
        mAdapterBookVertical.setDisplayMetrics(mDisplayMetrics)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_all_books_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initDisplayMetrics() {
        mDisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
    }
}