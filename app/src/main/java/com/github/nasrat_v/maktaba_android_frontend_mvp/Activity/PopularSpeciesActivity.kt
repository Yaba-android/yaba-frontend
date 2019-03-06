package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModelRandomFactory
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.BigListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Vertical.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Popular_species.Horizontal.PSModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

@SuppressLint("Registered")
class PopularSpeciesActivity : AppCompatActivity(),
    IBookClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private var mLastClickTime: Long = 0

    private lateinit var selectedPSpecies: PSModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popular_species_structure)

        selectedPSpecies = intent.getParcelableExtra("SelectedPopularSpecies")
        setListenerLibraryButtonFooter()
        setListenerBrowseButtonFooter()
        setListenerRecommendedButtonFooter()

        initDrawerLayout()
        initTitle()
        initVerticalRecycler()
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra("SelectedBook", book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_back_arrow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_back) {
            finish()
        }
        return true
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
        mDrawerLayout.setDrawerListener(mDrawerToggle)
    }

    private fun setListenerRecommendedButtonFooter() {
        val intent = Intent(this, RecommendedActivity::class.java)
        val buttonBrowse = findViewById<Button>(R.id.button_recommended_footer)

        buttonBrowse.setOnClickListener {
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    private fun initTitle() {
        val title = findViewById<TextView>(R.id.vertical_title)

        title.text = selectedPSpecies.name
    }

    private fun initVerticalRecycler() {
        val mDataset = arrayListOf<NoTitleListBModel>()

        mockDatasetVerticalRecyclerView(mDataset)

        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_double_recyclerview)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val adapterBookVertical = BigListBRecyclerViewAdapter(this, mDataset, this)

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        /*verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_book_vertical_recycler_view)
        )*/
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }

    private fun mockDatasetVerticalRecyclerView(mDataset: ArrayList<NoTitleListBModel>) {
        /*val hmodelsOne = arrayListOf<BModel>()
        val hmodelsTwo = arrayListOf<BModel>()
        val hmodelsThree = arrayListOf<BModel>()
        val hmodelsFour = arrayListOf<BModel>()
        val hmodelsFive = arrayListOf<BModel>()
        val hmodelsSix = arrayListOf<BModel>()
        val hmodelsSeven = arrayListOf<BModel>()
        val hmodelsHeigth = arrayListOf<BModel>()

        hmodelsOne.add(BModel(R.drawable.book1, "Here", "Taleb Al-Refai", 5f, 219, 10.99f))
        hmodelsOne.add(BModel(R.drawable.book2, "Black Leopard Red Wolf", "Marion James", 5f, 188, 9.99f))
        hmodelsTwo.add(BModel(R.drawable.book3, "Here", "Taleb Al-Refai", 5f, 219, 7.99f))
        hmodelsTwo.add(BModel(R.drawable.book4, "Black Leopard Red Wolf", "Marion James", 5f, 188, 4.10f))
        hmodelsThree.add(BModel(R.drawable.book5, "Here", "Taleb Al-Refai", 5f, 219, 8.00f))
        hmodelsThree.add(BModel(R.drawable.book6, "Black Leopard Red Wolf", "Marion James", 5f, 188, 5.99f))
        hmodelsFour.add(BModel(R.drawable.book7, "Here", "Taleb Al-Refai", 5f, 219, 9.90f))
        hmodelsFour.add(BModel(R.drawable.book8, "Black Leopard Red Wolf", "Marion James", 5f, 188, 19.99f))
        hmodelsFive.add(BModel(R.drawable.book3, "Here", "Taleb Al-Refai", 5f, 219, 13.99f))
        hmodelsFive.add(BModel(R.drawable.book5, "Black Leopard Red Wolf", "Marion James", 5f, 188, 6.20f))
        hmodelsSix.add(BModel(R.drawable.book4, "Here", "Taleb Al-Refai", 5f, 219, 8.99f))
        hmodelsSix.add(BModel(R.drawable.book1, "Black Leopard Red Wolf", "Marion James", 5f, 188, 4.20f))
        hmodelsSeven.add(BModel(R.drawable.book8, "Here", "Taleb Al-Refai", 5f, 219, 9.99f))
        hmodelsSeven.add(BModel(R.drawable.book1, "Black Leopard Red Wolf", "Marion James", 5f, 188, 5.90f))
        hmodelsHeigth.add(BModel(R.drawable.book3, "Here", "Taleb Al-Refai", 5f, 219, 9.99f))
        hmodelsHeigth.add(BModel(R.drawable.book6, "Black Leopard Red Wolf", "Marion James", 5f, 188, 6.99f))
        mDataset.add(NoTitleListBModel(hmodelsOne))
        mDataset.add(NoTitleListBModel(hmodelsTwo))
        mDataset.add(NoTitleListBModel(hmodelsThree))
        mDataset.add(NoTitleListBModel(hmodelsFour))
        mDataset.add(NoTitleListBModel(hmodelsFive))
        mDataset.add(NoTitleListBModel(hmodelsSix))
        mDataset.add(NoTitleListBModel(hmodelsSeven))
        mDataset.add(NoTitleListBModel(hmodelsHeigth))*/

        val factory = BModelRandomFactory(this)

        for (index in 0..7) {
            mDataset.add(NoTitleListBModel(factory.getRandomsInstances(2)))
        }
    }
}