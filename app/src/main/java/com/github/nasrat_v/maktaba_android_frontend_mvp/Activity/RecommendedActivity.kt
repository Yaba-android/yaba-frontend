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
import android.support.v4.view.GravityCompat
import android.support.v7.widget.*
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IRecommendedAdditionalClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Adapter.CarouselBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.LayoutManager.CarouselLinearLayoutManager
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.ListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.SmallListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.Horizontal.GPSRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.Vertical.GSRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.LeftOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.RightOffsetDecoration
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class RecommendedActivity : AppCompatActivity(),
    IBookClickCallback,
    IRecommendedAdditionalClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout

    companion object {
        const val SELECTED_BOOK = "SelectedBook"
        const val SELECTED_POPULAR_SPECIES = "SelectedPopularSpecies"
        const val LEFT_OR_RIGHT_IN_ANIMATION = "LeftOrRightInAnimation"
        const val TITLE_FIRST_RECYCLER_VIEW = "Recommended Authors"
        const val TITLE_SECOND_RECYCLER_VIEW = "Inspired by Your Reading History"
        const val TITLE_SMALL_RECYCLER_VIEW = "Other Books for you"
        const val TITLE_POPULAR_SPECIES_RECYCLER_VIEW = "Popular species"
    }

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_recommended_structure)

        setListenerButtonCloseGenre()
        setListenerButtonCloseProfile()
        setListenerBrowseButtonFooter()
        setListenerLibraryButtonFooter()

        initDrawerLayout()

        initCarouselRecycler()
        initFirstVerticalRecycler()
        initPopularSpeciesHorizontalRecycler()
        initSecondVerticalRecycler()
        initSmallVerticalRecycler()
        initSectionNavVerticalRecycler()
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

    override fun sectionEventButtonClicked(section: GModel) {
        // Open section page
    }

    override fun popularSpeciesEventButtonClicked(pspecies: GModel) {
        val intent = Intent(this, PopularSpeciesActivity::class.java)

        intent.putExtra(SELECTED_POPULAR_SPECIES, pspecies)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun killAllActivities() {
        val intent = Intent(this, RecommendedActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun setListenerButtonCloseGenre() {
        val nav = findViewById<NavigationView>(R.id.nav_view_section)
        val buttonCloseGenre = nav.findViewById<Button>(R.id.button_close_section)

        buttonCloseGenre.setOnClickListener {
            if (mDrawerLayout.isDrawerOpen(Gravity.END))
                mDrawerLayout.closeDrawer(Gravity.END)
            else
                mDrawerLayout.openDrawer(Gravity.END)
        }
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
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun setListenerLibraryButtonFooter() {
        val intent = Intent(this, LibraryActivity::class.java)
        val button = findViewById<Button>(R.id.button_library_footer)

        button.setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawerLayout = findViewById(R.id.drawer_recommended)
        //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
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

    private fun initCarouselRecycler() {
        val hmodels = mockDatasetDiscreteScrollView()
        val carouselRecyclerView = findViewById<RecyclerView>(R.id.carousel_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val linearManager = CarouselLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapterBookVertical =
            CarouselBRecyclerViewAdapter(
                this,
                hmodels
            )

        adapterBookVertical.setTabFragmentClickCallback(this)
        GravitySnapHelper(Gravity.START).attachToRecyclerView(carouselRecyclerView)
        carouselRecyclerView.setHasFixedSize(true)
        carouselRecyclerView.layoutManager = linearManager
        carouselRecyclerView.adapter = adapterBookVertical
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
        val mDataset = arrayListOf<ListBModel>()

        mockDatasetFirstRecyclerView(mDataset)

        val verticalRecyclerView = findViewById<RecyclerView>(R.id.first_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical =
            ListBRecyclerViewAdapter(
                this,
                mDataset,
                this
            )

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initPopularSpeciesHorizontalRecycler() {
        val popularList = GModelProvider(this).getPopularGenres()
        val layoutTitle = findViewById<RelativeLayout>(R.id.title_layout_genre)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val horizontalRecyclerView = findViewById<RecyclerView>(R.id.genre_horizontal_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterGenreHorizontal = GPSRecyclerViewAdapter(this, popularList, this)

        title.text = TITLE_POPULAR_SPECIES_RECYCLER_VIEW
        GravitySnapHelper(Gravity.END).attachToRecyclerView(horizontalRecyclerView)
        horizontalRecyclerView.setHasFixedSize(true)
        horizontalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.adapter = adapterGenreHorizontal
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
        val mDataset = arrayListOf<ListBModel>()

        mockDatasetSecondRecyclerView(mDataset)

        val verticalRecyclerView = findViewById<RecyclerView>(R.id.second_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical =
            ListBRecyclerViewAdapter(
                this,
                mDataset,
                this
            )

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initSmallVerticalRecycler() {
        val mDataset = arrayListOf<NoTitleListBModel>()

        mockDatasetSmallRecyclerView(mDataset)

        val layoutTitle = findViewById<RelativeLayout>(R.id.title_layout_small)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.small_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical =
            SmallListBRecyclerViewAdapter(
                this,
                mDataset,
                this
            )

        title.text = TITLE_SMALL_RECYCLER_VIEW
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_small_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initSectionNavVerticalRecycler() {
        val genreList = GModelProvider(this).getAllGenres()
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_recyclerview_section)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_section)
        val adapterBookVertical = GSRecyclerViewAdapter(this, genreList, this)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_section_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDatasetDiscreteScrollView(): ArrayList<BModel> {
        val factory = BModelRandomProvider(this)

        return factory.getRandomsInstancesDiscreteScrollView(15)
    }

    private fun mockDatasetFirstRecyclerView(mDataset: ArrayList<ListBModel>) {
        val factory = BModelRandomProvider(this)

        mDataset.add(
            ListBModel(
                TITLE_FIRST_RECYCLER_VIEW,
                factory.getRandomsInstances(8)
            )
        )
    }

    private fun mockDatasetSecondRecyclerView(mDataset: ArrayList<ListBModel>) {
        val factory = BModelRandomProvider(this)

        mDataset.add(
            ListBModel(
                TITLE_SECOND_RECYCLER_VIEW,
                factory.getRandomsInstances(8)
            )
        )
    }

    private fun mockDatasetSmallRecyclerView(mDataset: ArrayList<NoTitleListBModel>) {
        val factory = BModelRandomProvider(this)

        for (index in 0..1) {
            mDataset.add(
                NoTitleListBModel(
                    factory.getRandomsInstances(10)
                )
            )
        }
    }
}