package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.*
import com.folioreader.FolioReader
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.GroupListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

@SuppressLint("Registered")
class GroupActivity : AppCompatActivity(),
    IBookClickCallback {

    companion object {
        const val GROUP_NB_BOOK_PER_ROW = 2
        const val SELECTED_GROUP = "SelectedGroup"
    }

    private lateinit var mSelectedGroup: GroupBModel
    private lateinit var mDownloadedBooks: ArrayList<DownloadListBModel>
    private lateinit var mAdapterBookVertical: GroupListBRecyclerViewAdapter
    private lateinit var mDrawerLayout: DrawerLayout
    private val mFolioReader = FolioReader.get()
    private var mBooksToAddToDownload = arrayListOf<BModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_group_structure)

        mSelectedGroup = intent.getParcelableExtra(SELECTED_GROUP)
        mDownloadedBooks = intent.getParcelableArrayListExtra(LibraryActivity.DOWNLOADED_BOOKS)
        setGroupDetailsAttributes()

        setListenerBrowseButtonFooter()
        setListenerLibraryButtonFooter()
        setListenerRecommendedButtonFooter()

        initVerticalRecyclerView()
        initRootDrawerLayout()
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
            Toast.makeText(this, RecommendedActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            returnToHome()
            finishSendResult()
        }
    }

    private fun setListenerBrowseButtonFooter() {
        val intent = Intent(this, BrowseActivity::class.java)
        val button = findViewById<Button>(R.id.button_browse_footer)

        button.setOnClickListener {
            Toast.makeText(this, BrowseActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finishSendResult()
        }
    }

    private fun setListenerLibraryButtonFooter() {
        val intent = Intent(this, LibraryActivity::class.java)
        val button = findViewById<Button>(R.id.button_library_footer)

        button.setOnClickListener {
            Toast.makeText(this, LibraryActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finishSendResult()
        }
    }

    private fun finishSendResult() {
        if (!mBooksToAddToDownload.isEmpty()) {
            val intent = Intent()

            intent.putExtra(LibraryActivity.BOOKS_ADD_DOWNLOAD_LIST, mBooksToAddToDownload)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
        mBooksToAddToDownload.clear()
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun requestDownloadBook(book: BModel) {
        val dialog = Dialog(this, R.style.DownloadDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_action_book)

        val closeButton = dialog.findViewById<Button>(R.id.button_close_dialog)
        val actionButton = dialog.findViewById<Button>(R.id.button_action_dialog)

        actionButton.text = LibraryActivity.ACTION_BUTTON_TEXT_DOWNLOAD
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
        val dialog = Dialog(this, R.style.DownloadDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_action_book)

        val closeButton = dialog.findViewById<Button>(R.id.button_close_dialog)
        val actionButton = dialog.findViewById<Button>(R.id.button_action_dialog)

        actionButton.text = LibraryActivity.ACTION_BUTTON_TEXT_OPEN
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
        mBooksToAddToDownload.add(book)
        mAdapterBookVertical.notifyDataSetChangedDownloadList()
    }

    private fun openBook(book: BModel) {
        openFolioReader()
    }

    private fun isBookAlreadyDownloaded(book: BModel): Boolean {
        mDownloadedBooks.forEach { list ->
            if (list.bookModels.find { it.book == book } != null)
                return true
        }
        return false
    }

    private fun openFolioReader() {
        mFolioReader.openBook(LibraryActivity.PATH_TO_EBOOK_EPUB)
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
        mDrawerLayout.setDrawerListener(mDrawerToggle)
    }

    private fun getGroupBooksFormatedForAdapter(): ArrayList<NoTitleListBModel> {
        var noTitle: NoTitleListBModel
        var slice: Int
        val listNoTitleList = arrayListOf<NoTitleListBModel>()
        val sizeColumns = getNbColumns(GROUP_NB_BOOK_PER_ROW, mSelectedGroup.bookModels.size)
        val offset = if (mSelectedGroup.bookModels.size < GROUP_NB_BOOK_PER_ROW)
            mSelectedGroup.bookModels.size
        else
            GROUP_NB_BOOK_PER_ROW

        for (index in 0..(sizeColumns - 1)) {
            slice = (index * offset)
            noTitle = addBooksToRows(slice, (slice + offset), getNbBooksAdded(listNoTitleList))
            listNoTitleList.add(noTitle)
        }
        return listNoTitleList
    }

    private fun getNbBooksAdded(list: ArrayList<NoTitleListBModel>): Int {
        var nb = 0

        list.forEach {
            nb += it.bookModels.size
        }
        return nb
    }

    private fun addBooksToRows(firstSlice: Int, lastSlice: Int, nbBooksAdded: Int): NoTitleListBModel {
        return if (((mSelectedGroup.bookModels.size % 2) != 0)
            && (nbBooksAdded == (mSelectedGroup.bookModels.size - 1))
        ) { // si la taille est impair et que c'est le dernier item -> juste le dernier
            NoTitleListBModel(arrayListOf(mSelectedGroup.bookModels.last()))
        } else {
            NoTitleListBModel(
                ArrayList( // sinon de 2 en 2
                    mSelectedGroup.bookModels.subList(firstSlice, lastSlice)
                )
            )
        }
    }

    private fun getNbColumns(nbRows: Int, nbBooks: Int): Int {
        if ((nbBooks % 2) == 0) {
            return (nbBooks / nbRows)
        }
        return ((nbBooks / nbRows) + 1)
    }

    private fun initVerticalRecyclerView() {
        // on format en deux listes (vertical & horizontal) pour recylerviews
        val mDataset = getGroupBooksFormatedForAdapter()
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_double_recyclerview)

        mAdapterBookVertical =
            GroupListBRecyclerViewAdapter(
                this,
                mDataset,
                mDownloadedBooks,
                this
            )
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_all_books_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}