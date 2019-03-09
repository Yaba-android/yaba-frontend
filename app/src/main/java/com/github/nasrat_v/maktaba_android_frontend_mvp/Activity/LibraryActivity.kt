package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IBookClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ITabLayoutSetupCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.LibraryContainerFragment
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.TabLayoutCustomListener

class LibraryActivity : AppCompatActivity(),
    IBookClickCallback,
    ITabLayoutSetupCallback {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_library)

        setListenerButtonCloseProfile()
        setListenerBrowseButtonFooter()
        setListenerRecommendedButtonFooter()
        //setListenerButtonFolioReader()
        initRootDrawerLayout()
        if (savedInstanceState == null) {
            initFragmentManager()
        }
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START)
        else
            returnToHome()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun bookEventButtonClicked(book: BModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)

        intent.putExtra("SelectedBook", book)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val customListener = TabLayoutCustomListener(this)

        tabLayout.setupWithViewPager(viewPager)
        customListener.setTabTextToBold(tabLayout, tabLayout.selectedTabPosition)
        customListener.setListenerTabLayout(tabLayout)
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra("BrowseOrLibraryCall", 1)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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
        val button = findViewById<Button>(R.id.button_recommended_footer)

        button.setOnClickListener {
            returnToHome()
        }
    }

    private fun setListenerBrowseButtonFooter() {
        val intent = Intent(this, BrowseActivity::class.java)
        val buttonBrowse = findViewById<Button>(R.id.button_browse_footer)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        buttonBrowse.setOnClickListener {
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    /*private fun setListenerButtonFolioReader() {
        val folioReader = FolioReader.get()
        val openEpubReader = findViewById<Button>(R.id.open_epub_reader)

        openEpubReader.setOnClickListener {
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                folioReader.openBook("/storage/emulated/0/Download/pg58892-images.epub")
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }*/

    private fun initRootDrawerLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawerLayout = findViewById(R.id.drawer_library)
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

    private fun initFragmentManager() {
        val containerFragment = LibraryContainerFragment()
        val mFragmentManager = supportFragmentManager

        containerFragment.setBookClickCallback(this) // permet de gerer les click depuis le fragment
        //mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.fragment_container_library, containerFragment).commit()
    }
}