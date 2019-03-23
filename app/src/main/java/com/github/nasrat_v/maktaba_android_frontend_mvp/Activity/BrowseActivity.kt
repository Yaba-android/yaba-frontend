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
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Adapter.BrowseBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.ListEraseBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.github.nasrat_v.maktaba_android_frontend_mvp.Animation.BrowseActivityAnimation

@SuppressLint("Registered")
class BrowseActivity : AppCompatActivity(),
    IBookClickCallback,
    IDeleteBrowseBookClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mAdapterBookVertical: BrowseBRecyclerViewAdapter
    private lateinit var mAdapterBookSecondVertical: ListEraseBRecyclerViewAdapter
    private lateinit var mEditText: EditText
    private lateinit var mFirstVerticalRecyclerView: RecyclerView
    private lateinit var mSecondVerticalRecyclerView: RecyclerView
    private lateinit var mAllBooksFromDatabase: ArrayList<BModel>
    private lateinit var mFadeInResultAnim: Animation
    private lateinit var mFadeOutResultAnim: Animation
    private lateinit var mFadeInTitleEmptyAnim: Animation
    private lateinit var mFadeOutTitleEmptyAnim: Animation
    private lateinit var mFadeInContentEmptyAnim: Animation
    private lateinit var mFadeOutContentEmptyAnim: Animation
    private lateinit var mTitleEmpty: TextView
    private lateinit var mContentEmpty: TextView
    private lateinit var mTitleResults: TextView
    private val mListResultBrowse = arrayListOf<BModel>()
    private val mDatasetSecondRecyclerView = arrayListOf<ListBModel>()
    private var mInsertAnimationStart = false

    companion object {
        const val ACTIVITY_NAME = "Browse"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_browse_structure)

        fetchAllBooksFromDatabase()
        setListenerLibraryButtonFooter()
        setListenerRecommendedButtonFooter()

        initVerticalRecycler()
        initSecondVerticalRecycler()
        initEditText()
        initRootDrawerLayout()
        setFadeAnimation()
        startLaunchAnimation()
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
        startLaunchAnimation()
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra(RecommendedActivity.SELECTED_BOOK, book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        resetAllStates()
    }

    override fun bookEraseEventButtonClicked(book: BModel, position: Int) {
        if (mListResultBrowse.find { it == book } != null) {
            mListResultBrowse.removeAt(position)
            mAdapterBookVertical.notifyItemRemoved(position)
            if (mListResultBrowse.isEmpty())
                mFirstVerticalRecyclerView.visibility = View.GONE
        }
    }

    override fun recyclerViewEraseEventButtonClicked(position: Int) {
        mDatasetSecondRecyclerView.removeAt(position)
        mAdapterBookSecondVertical.notifyItemRemoved(position)
        if (mDatasetSecondRecyclerView.isEmpty())
            mSecondVerticalRecyclerView.visibility = View.GONE
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
        resetAllStates()
    }

    private fun resetAllStates() {
        val browseAnim = BrowseActivityAnimation(this)

        if (mListResultBrowse.isNotEmpty()) {
            browseAnim.resetAnimationFirstRecyclerView(mFirstVerticalRecyclerView, mListResultBrowse)
            mAdapterBookVertical.notifyDataSetChanged()
        }
        if (mDatasetSecondRecyclerView.isNotEmpty()) {
            browseAnim.resetAnimationSecondRecyclerView(mSecondVerticalRecyclerView, mDatasetSecondRecyclerView)
            mAdapterBookSecondVertical.notifyDataSetChanged()
        }
        if (mTitleResults.visibility == View.VISIBLE)
            browseAnim.resetAnimationTextView(mTitleResults)
        if (mTitleEmpty.visibility == View.VISIBLE)
            browseAnim.resetAnimationTextView(mTitleEmpty)
        if (mContentEmpty.visibility == View.VISIBLE)
            browseAnim.resetAnimationTextView(mContentEmpty)
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
            resetAllStates()
        }
    }

    private fun setListenerButtonCancel() {
        val buttonCancel = findViewById<Button>(R.id.button_cancel_browse)
        var firstRecyclerSize: Int
        var secondRecyclerSize: Int

        buttonCancel.setOnClickListener {
            mEditText.text.clear()
            firstRecyclerSize = mListResultBrowse.size
            secondRecyclerSize = mDatasetSecondRecyclerView.size
            mListResultBrowse.clear()
            mDatasetSecondRecyclerView.clear()
            setVisibilityTextRemove()
            notifyAllItemRemoved(firstRecyclerSize, secondRecyclerSize)
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

    private fun initVerticalRecycler() {
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_browse_book)

        mFirstVerticalRecyclerView = findViewById(R.id.vertical_browse_recyclerview)
        mAdapterBookVertical =
            BrowseBRecyclerViewAdapter(
                this,
                mListResultBrowse,
                this,
                this
            )
        mFirstVerticalRecyclerView.setHasFixedSize(true)
        mFirstVerticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mFirstVerticalRecyclerView.adapter = mAdapterBookVertical
        mFirstVerticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_browse_book_vertical_recycler_view)
        )
        mFirstVerticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initSecondVerticalRecycler() {
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_browse_book)

        mSecondVerticalRecyclerView = findViewById(R.id.vertical_browse_second_recyclerview)
        mAdapterBookSecondVertical =
            ListEraseBRecyclerViewAdapter(
                this,
                mDatasetSecondRecyclerView,
                this,
                this
            )
        mSecondVerticalRecyclerView.setHasFixedSize(true)
        mSecondVerticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mSecondVerticalRecyclerView.adapter = mAdapterBookSecondVertical
        mSecondVerticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initEditText() {
        val buttonConfirm = findViewById<Button>(R.id.button_confirm_browse)
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
        setListenerButtonCancel()
    }

    private fun setVisibilityTextRemove() {
        if (mTitleResults.visibility == View.VISIBLE) {
            mTitleResults.startAnimation(mFadeOutResultAnim)
        }
    }

    private fun setVisibilityItemInsert() {
        mTitleEmpty.startAnimation(mFadeOutTitleEmptyAnim)
        mContentEmpty.startAnimation(mFadeOutContentEmptyAnim)
    }

    private fun findDatasetSecondRecyclerView() {
        val genre: GModel = mListResultBrowse.first().genre
        val list: ArrayList<BModel>

        list = GModelProvider(this).getAllBooksFromGenre(genre)
        mDatasetSecondRecyclerView.add(ListBModel(("Category: " + genre.name), list))
    }

    private fun browseSearch() {
        val str = mEditText.text.toString().toLowerCase()

        mFirstVerticalRecyclerView.visibility = View.VISIBLE
        mSecondVerticalRecyclerView.visibility = View.VISIBLE
        clearAllDatasetRecyclerViews(mListResultBrowse.size, mDatasetSecondRecyclerView.size)
        mListResultBrowse.addAll(
            mAllBooksFromDatabase.filter {
                isSearchMatching(it, str)
            }
        )
        if (mListResultBrowse.isNotEmpty()) {
            findDatasetSecondRecyclerView()
            setVisibilityItemInsert()
            notifyAllItemInserted(mListResultBrowse.size, mDatasetSecondRecyclerView.size)
        }
    }

    private fun clearAllDatasetRecyclerViews(firstRecyclerSize: Int, secondRecyclerSize: Int) {
        if (mListResultBrowse.isNotEmpty() || mDatasetSecondRecyclerView.isNotEmpty()) {
            mListResultBrowse.clear()
            mDatasetSecondRecyclerView.clear()
            setVisibilityTextRemove()
            notifyAllItemRemoved(firstRecyclerSize, secondRecyclerSize)
        }
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

    private fun notifyAllItemRemoved(firstRecyclerSize: Int, secondRecyclerSize: Int) {
        mAdapterBookVertical.notifyItemRangeRemoved(0, firstRecyclerSize)
        mAdapterBookSecondVertical.notifyItemRangeRemoved(0, secondRecyclerSize)
    }

    private fun notifyAllItemInserted(firstRecyclerSize: Int, secondRecyclerSize: Int) {
        mAdapterBookVertical.notifyItemRangeInserted(0, firstRecyclerSize)
        mAdapterBookSecondVertical.notifyItemRangeInserted(0, secondRecyclerSize)
    }

    private fun setFadeAnimation() {
        mTitleEmpty = findViewById(R.id.title_browse_empty)
        mContentEmpty = findViewById(R.id.content_browse_empty)
        mTitleResults = findViewById(R.id.title_browse_results)

        setTitleResultAnimation()
        setTitleEmptyAnimation()
        setContentEmptyAnimation()
    }

    private fun startLaunchAnimation() {
        mTitleEmpty.startAnimation(mFadeInTitleEmptyAnim)
        mContentEmpty.startAnimation(mFadeInContentEmptyAnim)
    }

    private fun setTitleResultAnimation() {
        mFadeInResultAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        mFadeOutResultAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        mFadeInResultAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                mTitleResults.visibility = View.VISIBLE
            }

        })
        mFadeOutResultAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                mTitleResults.visibility = View.GONE
                mTitleEmpty.startAnimation(mFadeInTitleEmptyAnim)
                mContentEmpty.startAnimation(mFadeInContentEmptyAnim)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }

    private fun setTitleEmptyAnimation() {
        mFadeInTitleEmptyAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        mFadeOutTitleEmptyAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        mFadeInTitleEmptyAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                mTitleEmpty.visibility = View.VISIBLE
            }
        })
        mFadeOutTitleEmptyAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                mTitleEmpty.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
    }

    private fun setContentEmptyAnimation() {
        mFadeInContentEmptyAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        mFadeOutContentEmptyAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        mFadeInContentEmptyAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                mContentEmpty.visibility = View.VISIBLE
            }
        })
        mFadeOutContentEmptyAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                mContentEmpty.visibility = View.GONE
                mTitleResults.startAnimation(mFadeInResultAnim)
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
    }
}