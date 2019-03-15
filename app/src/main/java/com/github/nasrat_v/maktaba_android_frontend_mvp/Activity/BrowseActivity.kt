package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IDeleteBrowseBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Adapter.BrowseBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.ListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel

@SuppressLint("Registered")
class BrowseActivity : AppCompatActivity(),
    IBookClickCallback,
    IDeleteBrowseBookClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mAdapterBookVertical: BrowseBRecyclerViewAdapter
    private lateinit var mEditText: EditText
    private lateinit var mSecondVerticalRecyclerView: RecyclerView
    private lateinit var mAllBooksFromDatabase: ArrayList<BModel>
    private var mListResultBrowse = arrayListOf<BModel>()
    private var mListAllBooksDatabase = arrayListOf<BModel>()

    companion object {
        const val NB_ALL_BOOKS_DATABASE = 20
        const val NB_BOOKS_PER_ROW = 6
        const val ACTIVITY_NAME = "Browse"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_browse_structure)

        fetchAllBooksFromDatabase()
        setListenerLibraryButtonFooter()
        setListenerRecommendedButtonFooter()

        initListAllBooksDatabase()
        initVerticalRecycler()
        initSecondVerticalRecycler()
        initEditText()
        initRootDrawerLayout()
    }

    override fun onBackPressed() {
        returnToHome()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val anim = intent!!.getIntExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)

        if (anim == 0) // left
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        else if (anim == 1) // right
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra(RecommendedActivity.SELECTED_BOOK, book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun bookEraseEventButtonClicked(book: BModel) {
        if (mListResultBrowse.find { it == book } != null) {
            mListResultBrowse.remove(book)
            notifyDataSetChanged()
        }
    }

    private fun fetchAllBooksFromDatabase() {
        mAllBooksFromDatabase = BModelProvider(this).getAllBooksFromDatabase()
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 1)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setListenerRecommendedButtonFooter() {
        val button = findViewById<Button>(R.id.button_recommended_footer)

        button.setOnClickListener {
            Toast.makeText(this, RecommendedActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            returnToHome()
        }
    }

    private fun setListenerLibraryButtonFooter() {
        val intent = Intent(this, LibraryActivity::class.java)
        val button = findViewById<Button>(R.id.button_library_footer)

        button.setOnClickListener {
            Toast.makeText(this, LibraryActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initRootDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_browse)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawerLayout = findViewById(R.id.drawer_browse)
        val mDrawerToggle = ActionBarDrawerToggle(
            this, mDrawerLayout, toolbar,
            R.string.navigation_drawer_profile_open,
            R.string.navigation_drawer_profile_close
        )
        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)
    }

    private fun initListAllBooksDatabase() {
        mListAllBooksDatabase.addAll(BModelRandomProvider(this).getRandomsInstances(NB_ALL_BOOKS_DATABASE))
    }

    private fun initVerticalRecycler() {
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_browse_recyclerview)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_browse_book)
        mAdapterBookVertical =
            BrowseBRecyclerViewAdapter(
                this,
                mListResultBrowse,
                this,
                this
            )

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_browse_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initSecondVerticalRecycler() {
        val dataset = arrayListOf<ListBModel>()

        mockDatasetSecondRecyclerView(dataset)

        mSecondVerticalRecyclerView = findViewById(R.id.vertical_browse_second_recyclerview)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_browse_book)
        val adapterBookVertical =
            ListBRecyclerViewAdapter(
                this,
                dataset,
                this
            )

        mSecondVerticalRecyclerView.setHasFixedSize(true)
        mSecondVerticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mSecondVerticalRecyclerView.adapter = adapterBookVertical
        mSecondVerticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDatasetSecondRecyclerView(dataset: ArrayList<ListBModel>) {
        val factory = BModelRandomProvider(this)

        dataset.add(
            ListBModel(
                RecommendedActivity.TITLE_FIRST_RECYCLER_VIEW,
                factory.getRandomsInstancesFromList(
                    NB_BOOKS_PER_ROW,
                    mAllBooksFromDatabase
                )
            )
        )
    }

    private fun browseSearch() {
        val str = mEditText.text.toString().toLowerCase()

        mListResultBrowse.clear()
        mListResultBrowse.addAll(
            mListAllBooksDatabase.filter {
                isSearchMatching(it, str)
            }
        )
        notifyDataSetChanged()
    }

    private fun isSearchMatching(book: BModel, str: String): Boolean {
        return (book.title.toLowerCase() == str ||
                book.author.toLowerCase() == str ||
                book.country.toLowerCase() == str ||
                book.genre.name.toLowerCase() == str ||
                book.datePublication.toLowerCase() == str ||
                book.publisher.toLowerCase() == str)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus

        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun notifyDataSetChanged() {
        setVisibilityText()
        mAdapterBookVertical.notifyDataSetChanged()
    }

    private fun setVisibilityText() {
        val titleEmpty = findViewById<TextView>(R.id.title_browse_empty)
        val contentEmpty = findViewById<TextView>(R.id.content_browse_empty)
        val titleResults = findViewById<TextView>(R.id.title_browse_results)

        if (mListResultBrowse.isEmpty()) {
            titleEmpty.visibility = View.VISIBLE
            contentEmpty.visibility = View.VISIBLE
            titleResults.visibility = View.GONE
            mSecondVerticalRecyclerView.visibility = View.GONE
        } else {
            titleEmpty.visibility = View.GONE
            contentEmpty.visibility = View.GONE
            titleResults.visibility = View.VISIBLE
            mSecondVerticalRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun initEditText() {
        val buttonConfirm = findViewById<Button>(R.id.button_confirm_browse)
        val buttonCancel = findViewById<Button>(R.id.button_cancel_browse)
        mEditText = findViewById(R.id.edit_text_browse)

        mEditText.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                browseSearch()
            }
            true
        }
        buttonConfirm.setOnClickListener {
            hideKeyboard()
            browseSearch()
        }
        buttonCancel.setOnClickListener {
            mEditText.text.clear()
            mListResultBrowse.clear()
            notifyDataSetChanged()
        }
        setVisibilityText()
    }
}