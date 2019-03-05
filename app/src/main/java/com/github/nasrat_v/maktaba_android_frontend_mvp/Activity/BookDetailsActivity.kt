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
import android.view.*
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.BookDetailsContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Popular_species.Horizontal.PSModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Section.Vertical.SModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.TabLayoutCustomListener

class BookDetailsActivity : AppCompatActivity(),
    IBookClickCallback,
    ITabLayoutSetupCallback {

    private lateinit var selectedBook: BModel
    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_details)

        selectedBook = intent.getParcelableExtra("SelectedBook")
        setBookDetailsAttributes()
        initRootDrawerLayout()
        if (savedInstanceState == null) {
            initFragmentManager()
        }
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

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra("SelectedBook", book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val customListener = TabLayoutCustomListener()

        tabLayout.setupWithViewPager(viewPager)
        customListener.setTabTextToBold(tabLayout, tabLayout.selectedTabPosition)
        customListener.setListenerTabLayout(tabLayout)
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

    private fun initRootDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_book_details)

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
