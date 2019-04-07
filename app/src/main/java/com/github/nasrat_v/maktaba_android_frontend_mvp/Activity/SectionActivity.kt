package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask.SectionNoTitleListBModelAsynFetchData
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.BigListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Language.StringLocaleResolver
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

@SuppressLint("Registered")
class SectionActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<ArrayList<NoTitleListBModel>>,
    IBookClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mSelectedSection: GModel
    private lateinit var mAdapterBookVertical: BigListBRecyclerViewAdapter
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mDisplayMetrics: DisplayMetrics
    private val mDataset = arrayListOf<NoTitleListBModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    private var mFirstInit = true

    companion object {
        const val NB_BOOKS_PER_ROW = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_section_structure)

        localeOnNewIntent()

        mSelectedSection = intent.getParcelableExtra(RecommendedActivity.SELECTED_POPULAR_SPECIES)
        mProgressBar = findViewById(R.id.progress_bar_section)
        mFirstInit = true

        initDisplayMetrics()
        initDrawerLayout()
        initVerticalRecycler()
    }

    override fun onStart() {
        super.onStart()

        if (mFirstInit) {
            setListenerLibraryButtonFooter()
            setListenerBrowseButtonFooter()
            setListenerRecommendedButtonFooter()

            supportLoaderManager.initLoader(0, null, this).forceLoad() // init NoTitleListBModel in async task
        }
        mFirstInit = false
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<ArrayList<NoTitleListBModel>> {
        return SectionNoTitleListBModelAsynFetchData(this, mSelectedSection, mLanguage)
    }

    override fun onLoadFinished(p0: Loader<ArrayList<NoTitleListBModel>>, data: ArrayList<NoTitleListBModel>?) {
        mDataset.clear()
        mDataset.addAll(data!!)
        initTitle()
        mAdapterBookVertical.notifyDataSetChanged()
        mProgressBar.visibility = View.GONE
    }

    override fun onLoaderReset(p0: Loader<ArrayList<NoTitleListBModel>>) {
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra(RecommendedActivity.SELECTED_BOOK, book)
        startNewActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_back_arrow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_back) {
            returnToHome()
        }
        return true
    }

    private fun setListenerRecommendedButtonFooter() {
        val buttonBrowse = findViewById<Button>(R.id.button_recommended_footer)

        buttonBrowse.setOnClickListener {
            returnToHome()
        }
    }

    private fun setListenerBrowseButtonFooter() {
        val intent = Intent(this, BrowseActivity::class.java)
        val button = findViewById<Button>(R.id.button_browse_footer)

        button.setOnClickListener {
            startNewActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)
        startNewActivity(intent)
        finish()
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
        val title = findViewById<TextView>(R.id.vertical_title)

        title.text = mSelectedSection.name
    }

    private fun initDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawerLayout = findViewById(R.id.drawer_popular_species)
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
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_double_recyclerview)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val sortButton = findViewById<Button>(R.id.sort_button)

        mAdapterBookVertical =
            BigListBRecyclerViewAdapter(
                this,
                mDataset,
                this,
                mLanguage
            )
        sortButton.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.sort))
        mAdapterBookVertical.setDisplayMetrics(mDisplayMetrics)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_big_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initDisplayMetrics() {
        mDisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
    }
}