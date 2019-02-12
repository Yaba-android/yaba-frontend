package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.WindowManager

class BookDetailsActivity : AppCompatActivity(),
    ITabFragmentClickCallback, ITabLayoutSetupCallback {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        mDrawerLayout = findViewById(R.id.drawer_book_details)
        val mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
            R.string.navigation_drawer_profile_open, R.string.navigation_drawer_profile_close)
        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)

        val containerFragment = BookDetailsContainerFragment()
        containerFragment.setTabFragmentClickCallback(this) // permet de gerer les click depuis le fragment

        if (savedInstanceState == null) {
            val mFragmentManager = supportFragmentManager
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val mFragmentTransaction = mFragmentManager.beginTransaction()
            mFragmentTransaction.replace(R.id.fragment_container_book_details, containerFragment).commit()
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById<TabLayout>(R.id.tabs_book_details)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun bookEventButtonClicked() {
        val intent = Intent(this, BookDetailsActivity::class.java)

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
