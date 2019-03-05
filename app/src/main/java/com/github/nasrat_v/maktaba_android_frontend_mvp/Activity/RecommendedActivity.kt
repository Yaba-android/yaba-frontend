package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.Button
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.LibraryContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import android.os.SystemClock
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.DiscreteScrollViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IRecommendedAdditionalClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Popular_species.Horizontal.PSModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Section.Vertical.SModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Popular_species.Horizontal.PSRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Section.Vertical.SRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Section.Vertical.SRecyclerViewBottomOffsetDecoration
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class RecommendedActivity : AppCompatActivity(),
    IBookClickCallback,
    IRecommendedAdditionalClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private var mLastClickTime: Long = 0

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recommended_structure)

        setListenerButtonCloseGenre()
        setListenerButtonCloseProfile()
        setListenerBrowseButtonFooter()
        setListenerLibraryButtonFooter()

        initDrawerLayout()

        initDiscreteScrollView()
        initFirstVerticalRecycler()
        initPopularSpeciesHorizontalRecycler()
        initSecondVerticalRecycler()
        initSmallVerticalRecycler()
        initSectionNavVerticalRecycler()
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra("SelectedBook", book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun sectionEventButtonClicked(section: SModel) {
        // Open section page
    }

    override fun popularSpeciesEventButtonClicked(pspecies: PSModel) {
        val intent = Intent(this, PopularSpeciesActivity::class.java)

        intent.putExtra("SelectedPopularSpecies", pspecies)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    private fun setListenerLibraryButtonFooter() {
        val intent = Intent(this, LibraryActivity::class.java)
        val button = findViewById<Button>(R.id.button_library_footer)

        button.setOnClickListener {
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
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

    private fun initDiscreteScrollView() {
        val hmodels = arrayListOf<BModel>()

        mockDatasetDiscreteScrollView(hmodels)

        val discreteScrollView = findViewById<DiscreteScrollView>(R.id.discrete_scroll_view)
        val discreteRecyclerViewAdapter = DiscreteScrollViewAdapter(this, hmodels)

        discreteRecyclerViewAdapter.setTabFragmentClickCallback(this)
        discreteScrollView.setHasFixedSize(true)
        discreteScrollView.adapter = discreteRecyclerViewAdapter
        discreteScrollView.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.68f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.BOTTOM)
                .build()
        )
        if (hmodels.size > 0) {
            discreteScrollView.scrollToPosition(1)
        }
    }

    private fun initFirstVerticalRecycler() {
        val mDataset = arrayListOf<ListBModel>()

        mockDatasetFirstRecyclerView(mDataset)

        val verticalRecyclerView = findViewById<RecyclerView>(R.id.first_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical = ListBRecyclerViewAdapter(this, mDataset, this)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    @SuppressLint("SetTextI18n")
    private fun initPopularSpeciesHorizontalRecycler() {
        val hmodels = arrayListOf<PSModel>()

        mockDatasetPopularSpeciesRecyclerView(hmodels)

        val layoutTitle = findViewById<RelativeLayout>(R.id.title_layout_genre)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val horizontalRecyclerView = findViewById<RecyclerView>(R.id.genre_horizontal_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterGenreHorizontal = PSRecyclerViewAdapter(this, hmodels, this)
        title.text = "Popular species"
        horizontalRecyclerView.setHasFixedSize(true)
        horizontalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.adapter = adapterGenreHorizontal
        /*verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_book_vertical_recycler_view)
        )*/
        horizontalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initSecondVerticalRecycler() {
        val mDataset = arrayListOf<ListBModel>()

        mockDatasetSecondRecyclerView(mDataset)

        val verticalRecyclerView = findViewById<RecyclerView>(R.id.second_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical = ListBRecyclerViewAdapter(this, mDataset, this)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    @SuppressLint("SetTextI18n")
    private fun initSmallVerticalRecycler() {
        val mDataset = arrayListOf<NoTitleListBModel>()

        mockDatasetSmallRecyclerView(mDataset)

        val layoutTitle = findViewById<RelativeLayout>(R.id.title_layout_small)
        val title = layoutTitle.findViewById<TextView>(R.id.vertical_title)
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.small_book_vertical_recyclerview_recommended)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_recommended)
        val adapterBookVertical = SmallListBRecyclerViewAdapter(this, mDataset, this)

        title.text = "Other Books for you"
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.small_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun initSectionNavVerticalRecycler() {
        val hmodels = arrayListOf<SModel>()

        mockDatasetSectionNavRecyclerView(hmodels)

        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_recyclerview_section)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_section)
        val adapterBookVertical = SRecyclerViewAdapter(this, hmodels, this)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            SRecyclerViewBottomOffsetDecoration(this, R.dimen.bottom_section_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDatasetDiscreteScrollView(hmodels : ArrayList<BModel>) {
        hmodels.add(BModel(R.drawable.book5_carousel, "Here", "Taleb Al-Refai", 5f, 219))
        hmodels.add(BModel(R.drawable.book1_carousel, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodels.add(BModel(R.drawable.book7_carousel, "The Friend", "Sigrid Hunez", 4f, 188))
        hmodels.add(BModel(R.drawable.book8_carousel, "The Friend", "Sigrid Hunez", 4f, 188))
    }

    private fun mockDatasetFirstRecyclerView(mDataset: ArrayList<ListBModel>) {
        val hmodelsOne = arrayListOf<BModel>()

        hmodelsOne.add(BModel(R.drawable.book1, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsOne.add(BModel(R.drawable.book2, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsOne.add(BModel(R.drawable.book3, "The Friend", "Sigrid Hunez", 4f, 188))
        mDataset.add(ListBModel("Recommended Authors", hmodelsOne))
    }

    private fun mockDatasetPopularSpeciesRecyclerView(hmodels : ArrayList<PSModel>) {
        hmodels.add(PSModel("Fiction"))
        hmodels.add(PSModel("Drama"))
        hmodels.add(PSModel("Horror"))
    }

    private fun mockDatasetSecondRecyclerView(mDataset: ArrayList<ListBModel>) {
        val hmodelsTwo = arrayListOf<BModel>()

        hmodelsTwo.add(BModel(R.drawable.book4, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsTwo.add(BModel(R.drawable.book5, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsTwo.add(BModel(R.drawable.book6, "The Friend", "Sigrid Hunez", 4f, 188))
        mDataset.add(ListBModel("Inspired by Your Reading History", hmodelsTwo))
    }

    private fun mockDatasetSmallRecyclerView(mDataset: ArrayList<NoTitleListBModel>) {
        val hmodelsOne = arrayListOf<BModel>()
        val hmodelsTwo = arrayListOf<BModel>()

        hmodelsOne.add(BModel(R.drawable.book1, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsOne.add(BModel(R.drawable.book2, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsOne.add(BModel(R.drawable.book3, "The Friend", "Sigrid Hunez", 4f, 188))
        hmodelsOne.add(BModel(R.drawable.book4, "The Friend", "Sigrid Hunez", 4f, 188))
        hmodelsTwo.add(BModel(R.drawable.book5, "Here", "Taleb Al-Refai", 5f, 219))
        hmodelsTwo.add(BModel(R.drawable.book6, "Black Leopard Red Wolf", "Marion James", 5f, 188))
        hmodelsTwo.add(BModel(R.drawable.book7, "The Friend", "Sigrid Hunez", 4f, 188))
        hmodelsTwo.add(BModel(R.drawable.book8, "The Friend", "Sigrid Hunez", 4f, 188))
        mDataset.add(NoTitleListBModel(hmodelsOne))
        mDataset.add(NoTitleListBModel(hmodelsTwo))
    }

    private fun mockDatasetSectionNavRecyclerView(hmodels: ArrayList<SModel>) {
        hmodels.add(SModel("Art", 53))
        hmodels.add(SModel("Business", 74))
        hmodels.add(SModel("Computing", 47))
        hmodels.add(SModel("Education", 65))
        hmodels.add(SModel("Health", 76))
        hmodels.add(SModel("Home & Garden", 38))
        hmodels.add(SModel("Literature", 45))
        hmodels.add(SModel("Family", 163))
        hmodels.add(SModel("Professionals", 36))
        hmodels.add(SModel("Romantic", 53))
        hmodels.add(SModel("Science-Fiction", 44))
        hmodels.add(SModel("Personal Biography", 150))
        hmodels.add(SModel("Children Books", 98))
        hmodels.add(SModel("Crime & Excitement", 123))
        hmodels.add(SModel("Food & Drink", 108))
        hmodels.add(SModel("History", 52))
        hmodels.add(SModel("Humor", 21))
        hmodels.add(SModel("Non-Fiction", 248))
        hmodels.add(SModel("Policy", 112))
        hmodels.add(SModel("Debt", 98))
        hmodels.add(SModel("Science & Mathematics", 24))
        hmodels.add(SModel("Counseling", 48))
    }
}