package com.github.nasrat_v.yaba_demo.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import android.view.*
import android.widget.*
import com.bumptech.glide.Glide
import com.github.nasrat_v.yaba_demo.AsyncHydrater.BookDetailsBRModelAsyncHydrater
import com.github.nasrat_v.yaba_demo.ICallback.*
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.TabFragment.BookDetailsContainerFragment
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.Author.AModel
import com.github.nasrat_v.yaba_demo.Listable.Model.BookDetailsBRModel
import com.github.nasrat_v.yaba_demo.R
import com.github.nasrat_v.yaba_demo.Services.Provider.Author.AModelProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.ServerRoutesSingleton
import com.github.nasrat_v.yaba_demo.TabFragment.TabLayoutCustomListener

class BookDetailsActivity : AppCompatActivity(),
    androidx.loader.app.LoaderManager.LoaderCallbacks<BookDetailsBRModel>,
    IBookClickCallback,
    ITabLayoutSetupCallback,
    IBookInfosProvider,
    IBModelProviderCallback,
    IAModelProviderCallback {

    private lateinit var mSelectedBook: BModel
    private lateinit var mDrawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var mTabLayout: TabLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mContainerFragment: BookDetailsContainerFragment
    private lateinit var mBookDetailsBRModel: BookDetailsBRModel
    private lateinit var mProgressBar: ProgressBar
    private var mAuthorBooks = arrayListOf<BModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    private var mFirstInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_book_details)

        localeOnNewIntent()

        mFirstInit = true
        mProgressBar = findViewById(R.id.progress_bar_book_details)
        mSelectedBook = intent.getParcelableExtra(RecommendedActivity.SELECTED_BOOK)
        mContainerFragment = BookDetailsContainerFragment()
        mDrawerLayout = findViewById(R.id.drawer_book_details)
        mToolbar = findViewById(R.id.toolbar_book_details)
        mTabLayout = findViewById(R.id.tabs)

        initHeader()
        initRootDrawerLayout()
        if (savedInstanceState == null) {
            initFragmentManager()
        }
    }

    override fun onStart() {
        super.onStart()

        if (mFirstInit) {
            setListenerBrowseButtonFooter()
            setListenerRecommendedButtonFooter()
            setListenerLibraryButtonFooter()

            AModelProvider(this, mLanguage).getAuthorFromDatabase(mSelectedBook.author.remoteId, this)
        }
        mFirstInit = false
    }

    override fun onGetAllBooksRequestSuccess(allBooks: ArrayList<BModel>) {
        TODO("Not needed")
    }

    override fun onGetBookRequestSuccess(book: BModel) {
        mAuthorBooks.add(book)
        if (mAuthorBooks.size == mSelectedBook.author.booksId.size) {
            // init BookDetailsBRModel in async task when all authors book are saved
            supportLoaderManager.initLoader(0, null, this).forceLoad()
        }
    }

    override fun onGetAuthorRequestSuccess(author: AModel) {
        mSelectedBook.author = author
        mSelectedBook.author.booksId.forEach {
            BModelProvider(this, mLanguage).getBookFromDatabase(it, this)
        }
    }

    override fun onGetAllAuthorsRequestSuccess(authors: ArrayList<AModel>) {
        TODO("Not needed")
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): androidx.loader.content.Loader<BookDetailsBRModel> {
        return BookDetailsBRModelAsyncHydrater(this, mLanguage, mAuthorBooks)
    }

    override fun onLoadFinished(p0: androidx.loader.content.Loader<BookDetailsBRModel>, data: BookDetailsBRModel?) {
        mBookDetailsBRModel = data!!
        setBookDetailsAttributes()
        mContainerFragment.setBookDetailBRModelDataset(mBookDetailsBRModel)
        mProgressBar.visibility = View.GONE
    }

    override fun onLoaderReset(p0: androidx.loader.content.Loader<BookDetailsBRModel>) {
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
        startNewActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    override fun setupTabLayout(viewPager: androidx.viewpager.widget.ViewPager) {
        val customListener = TabLayoutCustomListener(this)

        mTabLayout.setupWithViewPager(viewPager)
        customListener.setTabTextToBold(mTabLayout, mTabLayout.selectedTabPosition)
        customListener.setListenerTabLayout(mTabLayout)
    }

    override fun getSelectedBook(): BModel {
        return mSelectedBook
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

    private fun setBookDetailsAttributes() {
        val image = findViewById<ImageView>(R.id.image_book)
        val title = findViewById<TextView>(R.id.title_book)
        val author = findViewById<TextView>(R.id.author_book)
        val ratingBar = findViewById<RatingBar>(R.id.rating_bar_book)
        val numberRating = findViewById<TextView>(R.id.number_rating_book)
        val buyButton = findViewById<Button>(R.id.button_buy_book)
        val url = (ServerRoutesSingleton.ROUTE_SRV_IMAGES + mSelectedBook.imagePath)

        // load image asynchronously with cache and placeholder
        Glide.with(this).load(url).placeholder(R.drawable.empty_book).into(image)
        title.text = mSelectedBook.title
        author.text = mSelectedBook.author.name
        ratingBar.rating = mSelectedBook.rating
        numberRating.text = ("(" + mSelectedBook.numberRating + ")")
        buyButton.text = (buyButton.text.toString() + " " + mSelectedBook.price + "$")
    }

    private fun startNewActivity(intent: Intent) {
        intent.putExtra(StringLocaleResolver.LANGUAGE_CODE, mLanguage)
        startActivity(intent)
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 1)
        startNewActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    private fun localeOnNewIntent() {
        mLanguage =
            intent.getStringExtra(StringLocaleResolver.LANGUAGE_CODE) ?: StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    }

    private fun initHeader() {
        val buttonWishList = findViewById<Button>(R.id.button_add_wishlist_book)
        val buttonBuy = findViewById<Button>(R.id.button_buy_book)
        val buttonTrySample = findViewById<Button>(R.id.button_try_sample_book)

        buttonWishList.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.add_to_wish_list))
        buttonBuy.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.buy))
        buttonTrySample.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.try_the_sample))
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
        mDrawerLayout.addDrawerListener(mDrawerToggle)
    }

    private fun initFragmentManager() {
        val mFragmentManager = supportFragmentManager

        mContainerFragment.setLanguageCode(mLanguage)
        mContainerFragment.setNumberRatingTabNameReview(mSelectedBook.numberRating)
        mContainerFragment.setTabFragmentClickCallback(this) // permet de gerer les click depuis le fragment
        mContainerFragment.setBookInfosProvider(this)
        mFragmentManager.popBackStackImmediate(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.fragment_container_book_details, mContainerFragment).commit()
    }
}
