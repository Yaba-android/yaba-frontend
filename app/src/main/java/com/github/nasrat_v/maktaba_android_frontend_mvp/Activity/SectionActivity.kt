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
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListAdapter.BigListBRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

@SuppressLint("Registered")
class SectionActivity : AppCompatActivity(),
    IBookClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mAllBooksFromDatabase: ArrayList<BModel>
    private lateinit var mSelectedSection: GModel

    companion object {
        const val NB_BOOKS_PER_ROW = 2
        const val NB_BOOKS_COLUMNS = 8
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_section_structure)

        mSelectedSection = intent.getParcelableExtra(RecommendedActivity.SELECTED_POPULAR_SPECIES)
        fetchAllBooksFromDatabase()
        setListenerLibraryButtonFooter()
        setListenerBrowseButtonFooter()
        setListenerRecommendedButtonFooter()

        initDrawerLayout()
        initTitle()
        initVerticalRecycler()
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

    private fun fetchAllBooksFromDatabase() {
        mAllBooksFromDatabase = BModelProvider(this).getAllBooksFromDatabase()
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
        val buttonBrowse = findViewById<Button>(R.id.button_recommended_footer)

        buttonBrowse.setOnClickListener {
            Toast.makeText(this, RecommendedActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setListenerBrowseButtonFooter() {
        val intent = Intent(this, BrowseActivity::class.java)
        val button = findViewById<Button>(R.id.button_browse_footer)

        button.setOnClickListener {
            Toast.makeText(this, BrowseActivity.ACTIVITY_NAME, Toast.LENGTH_SHORT).show()
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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

    private fun initTitle() {
        val title = findViewById<TextView>(R.id.vertical_title)

        title.text = mSelectedSection.name
    }

    private fun initVerticalRecycler() {
        val mDataset = GModelProvider(this).getListAllBooksFromGenre(NB_BOOKS_PER_ROW, mSelectedSection)

        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_double_recyclerview)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_double_book)
        val adapterBookVertical =
            BigListBRecyclerViewAdapter(
                this,
                mDataset,
                this
            )

        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = adapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_big_book_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}