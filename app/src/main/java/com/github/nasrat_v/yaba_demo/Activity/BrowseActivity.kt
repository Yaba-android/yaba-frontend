package com.github.nasrat_v.yaba_demo.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.WindowManager
import com.github.nasrat_v.yaba_demo.R
import android.widget.*
import com.github.nasrat_v.yaba_demo.ICallback.IBookClickCallback
import com.github.nasrat_v.yaba_demo.ICallback.IDeleteBrowseBookClickCallback
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.Adapter.BrowseBRecyclerViewAdapter
import com.github.nasrat_v.yaba_demo.Listable.BottomOffsetDecoration
import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListAdapter.ListEraseBRecyclerViewAdapter
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.github.nasrat_v.yaba_demo.AsyncTask.BrowseBModelAsyncHydrate
import com.github.nasrat_v.yaba_demo.ICallback.IInputBrowseCallback
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.Book.Model.BrowseBModel
import com.github.nasrat_v.yaba_demo.Services.Factory.Book.BrowseBModelFactory

@SuppressLint("Registered")
class BrowseActivity : AppCompatActivity(),
    androidx.loader.app.LoaderManager.LoaderCallbacks<BrowseBModel>,
    IBookClickCallback,
    IDeleteBrowseBookClickCallback,
    IInputBrowseCallback {

    private lateinit var mDrawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var mAdapterBookVertical: BrowseBRecyclerViewAdapter
    private lateinit var mAdapterBookSecondVertical: ListEraseBRecyclerViewAdapter
    private lateinit var mEditText: EditText
    private lateinit var mFirstVerticalRecyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var mSecondVerticalRecyclerView: androidx.recyclerview.widget.RecyclerView
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
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    private var mFirstInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_browse_structure)

        localeOnNewIntent()

        mFirstInit = true

        initTitle()
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

    override fun onCreateLoader(p0: Int, p1: Bundle?): androidx.loader.content.Loader<BrowseBModel> {
        return BrowseBModelAsyncHydrate(this, this, mLanguage)
    }

    override fun onLoadFinished(p0: androidx.loader.content.Loader<BrowseBModel>, data: BrowseBModel?) {
        mBrowseResult.booksResultList.clear()
        mBrowseResult.booksGenreList.clear()
        mBrowseResult.booksResultList.addAll(data!!.booksResultList)
        mBrowseResult.booksGenreList.addAll(data.booksGenreList)

        mTitleResults.startAnimation(mFadeInResultAnim)
        //notifyAllItemInserted(mBrowseResult.booksResultList.size, mBrowseResult.booksGenreList.size) not working ??
        notifyDataSetChanged()
    }

    override fun onLoaderReset(p0: androidx.loader.content.Loader<BrowseBModel>) {
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra(RecommendedActivity.SELECTED_BOOK, book)
        startNewActivity(intent)
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

    private fun setListenerRecommendedButtonFooter() {
        val intent = Intent(this, RecommendedActivity::class.java)
        val button = findViewById<Button>(R.id.button_recommended_footer)

        button.setOnClickListener {
            intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 1)
            startNewActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    private fun setListenerLibraryButtonFooter() {
        val intent = Intent(this, LibraryActivity::class.java)
        val button = findViewById<Button>(R.id.button_library_footer)

        button.setOnClickListener {
            startNewActivity(intent)
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

    private fun startNewActivity(intent: Intent) {
        intent.putExtra(StringLocaleResolver.LANGUAGE_CODE, mLanguage)
        startActivity(intent)
    }

    private fun localeOnNewIntent() {
        mLanguage =
            intent.getStringExtra(StringLocaleResolver.LANGUAGE_CODE) ?: StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    }

    private fun initTitle() {
        val titleEmpty = findViewById<TextView>(R.id.title_browse_empty)
        val contentEmpty = findViewById<TextView>(R.id.content_browse_empty)
        val titleResults = findViewById<TextView>(R.id.title_browse_results)

        titleEmpty.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.find_the_books_you_love))
        contentEmpty.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.search_by))
        titleResults.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.results))
    }

    private fun initEmptyBrowseResult() {
        mBrowseResult = BrowseBModelFactory().getEmptyInstance()
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
        mDrawerLayout.addDrawerListener(mDrawerToggle)
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
        mFirstVerticalRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
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
                this,
                mLanguage
            )
        mSecondVerticalRecyclerView.setHasFixedSize(true)
        mSecondVerticalRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
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