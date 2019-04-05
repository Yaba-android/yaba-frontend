package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import android.support.design.widget.NavigationView
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.view.GravityCompat
import android.support.v7.widget.*
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask.RecommendedBRModelAsyncFetchData
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IRecommendedAdditionalClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Language.LocaleHelper
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Adapter.CarouselBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.LayoutManager.CarouselLinearLayoutManager
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.NoTitleListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.SmallListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.Horizontal.GPSRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.LeftOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Model.RecommendedBRModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.RightOffsetDecoration
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class RecommendedActivity() : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<RecommendedBRModel>,
    IBookClickCallback,
    IRecommendedAdditionalClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mAdapterBookVerticalCarousel: CarouselBRecyclerViewAdapter
    private lateinit var mAdapterBookVerticalFirstRecyclerView: NoTitleListBRecyclerViewAdapter
    private lateinit var mAdapterGenreHorizontal: GPSRecyclerViewAdapter
    private lateinit var mAdapterBookVerticalSecondRecyclerView: NoTitleListBRecyclerViewAdapter
    private lateinit var mAdapterBookVerticalSmallRecyclerView: SmallListBRecyclerViewAdapter
    private lateinit var mProgressBar: ProgressBar
    private val mDatasetCarousel = arrayListOf<BModel>()
    private val mDatasetFirstRecyclerView = arrayListOf<NoTitleListBModel>()
    private val mPopularList = arrayListOf<GModel>()
    private val mDatasetSecondRecyclerView = arrayListOf<NoTitleListBModel>()
    private val mDatasetSmallRecyclerView = arrayListOf<NoTitleListBModel>()
    private var mFirstInit = true

    companion object {
        const val NB_BOOKS_CAROUSEL = 15
        const val NB_BOOKS_FIRST_RECYCLERVIEW = 6
        const val NB_BOOKS_SECOND_RECYCLERVIEW = 6
        const val NB_BOOKS_SMALL_RECYCLERVIEW = 8
        const val FIRST_RECYCLERVIEW_NB_COLUMNS = 1
        const val SECOND_RECYCLERVIEW_NB_COLUMNS = 1
        const val SMALL_RECYCLERVIEW_NB_COLUMNS = 2
        const val ACTIVITY_NAME = "Recommended"
        const val SELECTED_BOOK = "SelectedBook"
        const val SELECTED_POPULAR_SPECIES = "SelectedPopularSpecies"
        const val LEFT_OR_RIGHT_IN_ANIMATION = "LeftOrRightInAnimation"
        const val LANGUAGE_CODE = "ar"
    }

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended_structure)

        mProgressBar = findViewById(R.id.progress_bar_recommeded)
        mFirstInit = true

        initAllViews()
        initDrawerLayout()
    }

    override fun onStart() {
        super.onStart()

        if (mFirstInit) {
            setListenerButtonCloseProfile()
            setListenerBrowseButtonFooter()
            setListenerLibraryButtonFooter()
            setListenerButtonBrowseSection()
            setListenerViewAllSection()
            setListenerChangeLanguage()

            supportLoaderManager.initLoader(0, null, this).forceLoad() // init RecommendedBRModel in async task
        }
        mFirstInit = false
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<RecommendedBRModel> {
        return RecommendedBRModelAsyncFetchData(this)
    }

    override fun onLoadFinished(p0: Loader<RecommendedBRModel>, data: RecommendedBRModel?) {
        mDatasetCarousel.clear()
        mDatasetFirstRecyclerView.clear()
        mPopularList.clear()
        mDatasetSecondRecyclerView.clear()
        mDatasetSmallRecyclerView.clear()
        mDatasetCarousel.addAll(data!!.booksCarousel)
        mDatasetFirstRecyclerView.addAll(data.booksFirstRecyclerView)
        mPopularList.addAll(data.popularGenre)
        mDatasetSecondRecyclerView.addAll(data.booksSecondRecyclerView)
        mDatasetSmallRecyclerView.addAll(data.booksSmallRecyclerView)

        initTitle()
        mAdapterBookVerticalCarousel.notifyDataSetChanged()
        mAdapterBookVerticalFirstRecyclerView.notifyDataSetChanged()
        mAdapterGenreHorizontal.notifyDataSetChanged()
        mAdapterBookVerticalSecondRecyclerView.notifyDataSetChanged()
        mAdapterBookVerticalSmallRecyclerView.notifyDataSetChanged()
        mProgressBar.visibility = View.GONE
    }

    override fun onLoaderReset(p0: Loader<RecommendedBRModel>) {
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START)
        else {
            killAllActivities()
            super.onBackPressed()
            moveTaskToBack(false)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val anim = intent!!.getIntExtra(LEFT_OR_RIGHT_IN_ANIMATION, -1)

        if (anim == 0) // left
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        else if (anim == 1) // right
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra(SELECTED_BOOK, book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun popularSpeciesEventButtonClicked(pspecies: GModel) {
        val intent = Intent(this, SectionActivity::class.java)

        intent.putExtra(SELECTED_POPULAR_SPECIES, pspecies)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun initTitle() {
        val title = findViewById<TextView>(R.id.title_carousel)
        val titleBis = findViewById<TextView>(R.id.title_bis_carousel)

        title.text = getString(R.string.carousel_title_first)
        titleBis.text = getString(R.string.carousel_title_second)
    }

    private fun initAllViews() {
        initCarouselRecycler()
        initFirstVerticalRecycler()
        initPopularSpeciesHorizontalRecycler()
        initSecondVerticalRecycler()
        initSmallVerticalRecycler()
    }

    private fun killAllActivities() {
        val intent = Intent(this, RecommendedActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun setListenerButtonCloseProfile() {
        val nav = findViewById<NavigationView>(R.id.nav_view_profile)
        val header = nav.getHeaderView(0)
        val buttonCloseProfile = header.findViewById<Button>(R.id.button_close_profile)

        buttonCloseProfile.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setListenerBrowseButtonFooter() {
        val intent = Intent(this, BrowseActivity::class.java)
        val button = findViewById<Button>(R.id.button_browse_footer)

        button.setOnClickListener {
            Toast.makeText(this, BrowseActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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

    private fun setListenerButtonBrowseSection() {
        val button = findViewById<Button>(R.id.button_browse_sections)

        button.setOnClickListener {
            startSectionActivity()
        }
    }

    private fun setListenerViewAllSection() {
        val layout = findViewById<RelativeLayout>(R.id.title_layout_genre)
        val button = layout.findViewById<Button>(R.id.view_all_button)

        button.setOnClickListener {
            startSectionActivity()
        }
    }

    private fun setListenerChangeLanguage() {
        val button = findViewById<Button>(R.id.change_language_button)

        button.setOnClickListener {
            LocaleHelper.setLocale(this, LANGUAGE_CODE)
            recreate()
        }
    }

    private fun startSectionActivity() {
        val intent = Intent(this, SectionsActivity::class.java)

        Toast.makeText(this, SectionsActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun initDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawerLayout = findViewById(R.id.drawer_recommended)
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
        mDrawerLayout.addDrawerListener(mDrawerToggle)
    }

    private fun initCarouselRecycler() {
        val carouselRecyclerView = findViewById<RecyclerView>(R.id.carousel_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val linearManager = CarouselLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mAdapterBookVerticalCarousel =
            CarouselBRecyclerViewAdapter(
                this,
                mDatasetCarousel
            )
        mAdapterBookVerticalCarousel.setTabFragmentClickCallback(this)
        GravitySnapHelper(Gravity.START).attachToRecyclerView(carouselRecyclerView)
        carouselRecyclerView.setHasFixedSize(true)
        carouselRecyclerView.layoutManager = linearManager
        carouselRecyclerView.adapter = mAdapterBookVerticalCarousel
        carouselRecyclerView.addItemDecoration(
            LeftOffsetDecoration(this, R.dimen.left_carousel_recycler_view)
        )
        carouselRecyclerView.addItemDecoration(
            RightOffsetDecoration(this, R.dimen.right_carousel_recycler_view)
        )
        carouselRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initFirstVerticalRecycler() {
        val layoutTitle = findViewById<RelativeLayout>(R.id.title_layout_first)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.first_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)

        mAdapterBookVerticalFirstRecyclerView =
            NoTitleListBRecyclerViewAdapter(
                this,
                mDatasetFirstRecyclerView,
                this
            )
        title.text = getString(R.string.title_first_recyclerview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVerticalFirstRecyclerView
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initPopularSpeciesHorizontalRecycler() {
        val layoutTitle = findViewById<RelativeLayout>(R.id.title_layout_genre)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val horizontalRecyclerView = findViewById<RecyclerView>(R.id.genre_horizontal_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)

        mAdapterGenreHorizontal = GPSRecyclerViewAdapter(this, mPopularList, this)
        title.text = getString(R.string.title_popular_species_recyclerview)
        //GravitySnapHelper(Gravity.END).attachToRecyclerView(horizontalRecyclerView)
        horizontalRecyclerView.setHasFixedSize(true)
        horizontalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.adapter = mAdapterGenreHorizontal
        horizontalRecyclerView.addItemDecoration(
            LeftOffsetDecoration(this, R.dimen.left_popular_genre_horizontal_recycler_view)
        )
        horizontalRecyclerView.addItemDecoration(
            RightOffsetDecoration(this, R.dimen.right_popular_genre_horizontal_recycler_view)
        )
        horizontalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initSecondVerticalRecycler() {
        val layoutTitle = findViewById<RelativeLayout>(R.id.title_layout_second)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.second_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)

        mAdapterBookVerticalSecondRecyclerView =
            NoTitleListBRecyclerViewAdapter(
                this,
                mDatasetSecondRecyclerView,
                this
            )
        title.text = getString(R.string.title_second_recyclerview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVerticalSecondRecyclerView
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initSmallVerticalRecycler() {
        val layoutTitle = findViewById<RelativeLayout>(R.id.title_layout_small)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.small_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)

        mAdapterBookVerticalSmallRecyclerView =
            SmallListBRecyclerViewAdapter(
                this,
                mDatasetSmallRecyclerView,
                this
            )
        title.text = getString(R.string.title_small_recyclerview)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVerticalSmallRecyclerView
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_small_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}