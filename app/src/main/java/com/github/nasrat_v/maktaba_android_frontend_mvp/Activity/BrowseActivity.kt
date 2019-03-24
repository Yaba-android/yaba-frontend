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
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.ListEraseBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask.BrowseBModelAsynFetchData
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IInputBrowseCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Model.BrowseBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Book.BrowseBModelFactory
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelProvider

@SuppressLint("Registered")
class BrowseActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<BrowseBModel>,
    IBookClickCallback,
    IDeleteBrowseBookClickCallback,
    IInputBrowseCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mAdapterBookVertical: BrowseBRecyclerViewAdapter
    private lateinit var mAdapterBookSecondVertical: ListEraseBRecyclerViewAdapter
    private lateinit var mEditText: EditText
    private lateinit var mFirstVerticalRecyclerView: RecyclerView
    private lateinit var mSecondVerticalRecyclerView: RecyclerView
    private lateinit var mFadeInResultAnim: Animation
    private lateinit var mFadeOutResultAnim: Animation
    private lateinit var mFadeInTitleEmptyAnim: Animation
    private lateinit var mFadeOutTitleEmptyAnim: Animation
    private lateinit var mFadeInContentEmptyAnim: Animation
    private lateinit var mFadeOutContentEmptyAnim: Animation
    private lateinit var mTitleEmpty: TextView
    private lateinit var mContentEmpty: TextView
    private lateinit var mTitleResults: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mBrowseResult: BrowseBModel
    private var mInputBrowseString = String()
    private var mFirstInit = true

    companion object {
        const val ACTIVITY_NAME = "Browse"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_browse_structure)
        mFirstInit = true

        initEmptyBrowseResult()
        initVerticalRecycler()
        initSecondVerticalRecycler()
        initRootDrawerLayout()
    }

    override fun onStart() {
        super.onStart()

        if (mFirstInit) {
            setListenerLibraryButtonFooter()
            setListenerRecommendedButtonFooter()

            initEditText()
            setFadeAnimation()
            startLaunchAnimation()
        }
        mFirstInit = false
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<BrowseBModel> {
        return BrowseBModelAsynFetchData(this, this)
    }

    override fun onLoadFinished(p0: Loader<BrowseBModel>, data: BrowseBModel?) {
        mBrowseResult.booksResultList.clear()
        mBrowseResult.booksGenreList.clear()
        mBrowseResult.booksResultList.addAll(data!!.booksResultList)
        mBrowseResult.booksGenreList.addAll(data.booksGenreList)

        mTitleResults.startAnimation(mFadeInResultAnim)
        //notifyAllItemInserted(mBrowseResult.booksResultList.size, mBrowseResult.booksGenreList.size) not working ??
        notifyDataSetChanged()
    }

    override fun onLoaderReset(p0: Loader<BrowseBModel>) {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra(RecommendedActivity.SELECTED_BOOK, book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun bookEraseEventButtonClicked(book: BModel, position: Int) {
        if (mBrowseResult.booksResultList.find { it == book } != null) {
            mBrowseResult.booksResultList.removeAt(position)
            mAdapterBookVertical.notifyItemRemoved(position)
            if (mBrowseResult.booksResultList.isEmpty())
                mFirstVerticalRecyclerView.visibility = View.GONE
        }
    }

    override fun recyclerViewEraseEventButtonClicked(position: Int) {
        mBrowseResult.booksGenreList.removeAt(position)
        mAdapterBookSecondVertical.notifyItemRemoved(position)
        if (mBrowseResult.booksGenreList.isEmpty())
            mSecondVerticalRecyclerView.visibility = View.GONE
    }

    override fun getInputBrowseString(): String {
        return mInputBrowseString
    }

    private fun initEmptyBrowseResult() {
        mBrowseResult = BrowseBModelFactory().getEmptyInstance()
    }

    private fun setListenerRecommendedButtonFooter() {
        val intent = Intent(this, RecommendedActivity::class.java)
        val button = findViewById<Button>(R.id.button_recommended_footer)

        button.setOnClickListener {
            Toast.makeText(this, RecommendedActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 1)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
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
            finish()
        }
    }

    private fun setListenerButtonCancel() {
        val buttonCancel = findViewById<Button>(R.id.button_cancel_browse)
        var firstRecyclerSize: Int
        var secondRecyclerSize: Int

        buttonCancel.setOnClickListener {
            mEditText.text.clear()
            firstRecyclerSize = mBrowseResult.booksResultList.size
            secondRecyclerSize = mBrowseResult.booksGenreList.size
            mProgressBar.visibility = View.GONE
            mBrowseResult.booksResultList.clear()
            mBrowseResult.booksGenreList.clear()
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
                mBrowseResult.booksResultList,
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
                mBrowseResult.booksGenreList,
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
        mProgressBar = findViewById(R.id.progress_bar_browse)
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

    private fun browseSearch() {
        setVisibilityItemInsert()
        mProgressBar.visibility = View.VISIBLE
        mFirstVerticalRecyclerView.visibility = View.VISIBLE
        mSecondVerticalRecyclerView.visibility = View.VISIBLE
        clearAllDatasetRecyclerViews(mBrowseResult.booksResultList.size, mBrowseResult.booksGenreList.size)
        mInputBrowseString = mEditText.text.toString().toLowerCase()

        supportLoaderManager.restartLoader(0, null, this).forceLoad() // launch search in async task -> restart loader each time
    }

    private fun clearAllDatasetRecyclerViews(firstRecyclerSize: Int, secondRecyclerSize: Int) {
        if (mBrowseResult.booksResultList.isNotEmpty() || mBrowseResult.booksGenreList.isNotEmpty()) {
            mBrowseResult.booksResultList.clear()
            mBrowseResult.booksGenreList.clear()
            setVisibilityTextRemove()
            notifyAllItemRemoved(firstRecyclerSize, secondRecyclerSize)
        }
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

    // not working with async task ???
    private fun notifyAllItemInserted(firstRecyclerSize: Int, secondRecyclerSize: Int) {
        mAdapterBookVertical.notifyItemRangeInserted(0, firstRecyclerSize)
        mAdapterBookSecondVertical.notifyItemRangeInserted(0, secondRecyclerSize)
    }

    private fun notifyDataSetChanged() {
        mAdapterBookVertical.notifyDataSetChanged()
        mAdapterBookSecondVertical.notifyDataSetChanged()
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
                mProgressBar.visibility = View.GONE
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
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
    }
}