package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.BookDetailsContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabFragmentClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class BookDetailsActivity : AppCompatActivity(),
    ITabFragmentClickCallback, ITabLayoutSetupCallback {

    private lateinit var selectedBook: BModel
    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_details)

        selectedBook = intent.getParcelableExtra("SelectedBook")
        setBookDetailsAttributes()
        setListenerBackButtonToolbar()
        initRootDrawerLayout()
        if (savedInstanceState == null) {
            initFragmentManager()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_toolbar_menu, menu)
        return true
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById<TabLayout>(R.id.tabs)

        tabLayout.setupWithViewPager(viewPager)
        setTabTextToBold(tabLayout, tabLayout.selectedTabPosition)
        setListenerTabLayout(tabLayout)
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra("SelectedBook", book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun setBookDetailsAttributes() {
        val image = findViewById<ImageView>(R.id.image_book)
        val title = findViewById<TextView>(R.id.title_book)
        val author = findViewById<TextView>(R.id.author_book)
        val ratingBar = findViewById<RatingBar>(R.id.rating_bar_book)
        val numberRating = findViewById<TextView>(R.id.number_rating_book)

        image.setImageResource(selectedBook.image)
        title.text = selectedBook.title
        author.text = selectedBook.author
        ratingBar.rating = selectedBook.rating
        numberRating.text = ("(" + selectedBook.numberRating + ")")
    }

    private fun setListenerTabLayout(tabLayout: TabLayout) {
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                setTabTextToBold(tabLayout, tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                setTabTextToNormal(tabLayout, tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    private fun setListenerBackButtonToolbar() {
        val arrowBack = findViewById<Button>(R.id.button_back_to_store_toolbar)

        arrowBack.setOnClickListener {
            finish()
        }
    }

    private fun setTabTextToBold(tabLayout: TabLayout, indexTab: Int) {
        val linearLayout = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(indexTab) as LinearLayout
        val tabTextView = linearLayout.getChildAt(1) as TextView

        tabTextView.setTypeface(tabTextView.typeface, Typeface.BOLD)
    }

    private fun setTabTextToNormal(tabLayout: TabLayout, indexTab: Int) {
        val linearLayout = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(indexTab) as LinearLayout
        val tabTextView = linearLayout.getChildAt(1) as TextView

        tabTextView.setTypeface(null, Typeface.NORMAL)
    }

    private fun initRootDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawerLayout = findViewById(R.id.drawer_book_details)
        val mDrawerToggle = ActionBarDrawerToggle(
            this, mDrawerLayout, toolbar,
            R.string.navigation_drawer_profile_open,
            R.string.navigation_drawer_profile_close
        )
        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)
    }

    private fun initFragmentManager() {
        val containerFragment = BookDetailsContainerFragment()
        val mFragmentManager = supportFragmentManager

        containerFragment.setNumberRatingTabNameReview(selectedBook.numberRating)
        containerFragment.setTabFragmentClickCallback(this) // permet de gerer les click depuis le fragment
        mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.fragment_container_book_details, containerFragment).commit()
    }
}
