package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.*
import com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask.BookDetailsBRModelAsyncFetchData
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.BookDetailsContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookInfosProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Model.BookDetailsBRModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.TabLayoutCustomListener

class BookDetailsActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<BookDetailsBRModel>,
    IBookClickCallback,
    ITabLayoutSetupCallback,
    IBookInfosProvider {

    private lateinit var mSelectedBook: BModel
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mTabLayout: TabLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mContainerFragment: BookDetailsContainerFragment
    private lateinit var mBookDetailsBRModel: BookDetailsBRModel
    private lateinit var mProgressBar: ProgressBar
    private var mFirstInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_book_details)

        mFirstInit = true
        mProgressBar = findViewById(R.id.progress_bar_book_details)
        mSelectedBook = intent.getParcelableExtra(RecommendedActivity.SELECTED_BOOK)
        mContainerFragment = BookDetailsContainerFragment()
        mDrawerLayout = findViewById(R.id.drawer_book_details)
        mToolbar = findViewById(R.id.toolbar_book_details)
        mTabLayout = findViewById(R.id.tabs)

        initRootDrawerLayout()
        if (savedInstanceState == null) {
            initFragmentManager()
        }
    }

    override fun onStart() {
        super.onStart()

        if (mFirstInit) {
            setListenerButtonCloseProfile()
            setListenerBrowseButtonFooter()
            setListenerRecommendedButtonFooter()
            setListenerLibraryButtonFooter()

            supportLoaderManager.initLoader(0, null, this).forceLoad() // init BookDetailsBRModel in async task
        }
        mFirstInit = false
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<BookDetailsBRModel> {
        return BookDetailsBRModelAsyncFetchData(this)
    }

    override fun onLoadFinished(p0: Loader<BookDetailsBRModel>, data: BookDetailsBRModel?) {
        mBookDetailsBRModel = data!!
        setBookDetailsAttributes()
        mContainerFragment.setBookDetailBRModelDataset(mBookDetailsBRModel)
        mProgressBar.visibility = View.GONE
    }

    override fun onLoaderReset(p0: Loader<BookDetailsBRModel>) {
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra(RecommendedActivity.SELECTED_BOOK, book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val customListener = TabLayoutCustomListener(this)

        mTabLayout.setupWithViewPager(viewPager)
        customListener.setTabTextToBold(mTabLayout, mTabLayout.selectedTabPosition)
        customListener.setListenerTabLayout(mTabLayout)
    }

    override fun getSelectedBook(): BModel {
        return mSelectedBook
    }

    private fun setListenerButtonCloseProfile() {
        val nav = findViewById<NavigationView>(R.id.nav_view_profile)
        val header = nav.getHeaderView(0)
        val buttonCloseProfile = header.findViewById<Button>(R.id.button_close_profile)

        buttonCloseProfile.setOnClickListener {
            onBackPressed()
        }
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

    private fun setBookDetailsAttributes() {
        val image = findViewById<ImageView>(R.id.image_book)
        val title = findViewById<TextView>(R.id.title_book)
        val author = findViewById<TextView>(R.id.author_book)
        val ratingBar = findViewById<RatingBar>(R.id.rating_bar_book)
        val numberRating = findViewById<TextView>(R.id.number_rating_book)
        val buyButton = findViewById<Button>(R.id.button_buy_book)

        image.setImageResource(mSelectedBook.image)
        title.text = mSelectedBook.title
        author.text = mSelectedBook.author.name
        ratingBar.rating = mSelectedBook.rating
        numberRating.text = ("(" + mSelectedBook.numberRating + ")")
        buyButton.text = ("$" + mSelectedBook.price + " " + buyButton.text)
    }

    private fun initRootDrawerLayout() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mDrawerToggle = ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar,
            R.string.navigation_drawer_profile_open,
            R.string.navigation_drawer_profile_close
        )
        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)
    }

    private fun initFragmentManager() {
        val mFragmentManager = supportFragmentManager

        mContainerFragment.setNumberRatingTabNameReview(mSelectedBook.numberRating)
        mContainerFragment.setTabFragmentClickCallback(this) // permet de gerer les click depuis le fragment
        mContainerFragment.setBookInfosProvider(this)
        mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.fragment_container_book_details, mContainerFragment).commit()
    }
}
