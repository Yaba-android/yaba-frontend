package com.github.nasrat_v.maktaba_android_frontend_mvp

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

class BookDetailsActivity : AppCompatActivity(), BookDetailsContainerFragment.TabLayoutSetupCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mFragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_book_details)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        mDrawerLayout = findViewById(R.id.drawer_book_details)
        val mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
            R.string.navigation_drawer_profile_open, R.string.navigation_drawer_profile_close)
        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)

        val containerFragment = BookDetailsContainerFragment()
        //containerFragment.setClickCallback(this) // permet de gerer les click depuis le fragment

        if (savedInstanceState == null) {
            mFragmentManager = supportFragmentManager
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            mFragmentTransaction = mFragmentManager.beginTransaction()
            mFragmentTransaction.replace(R.id.fragment_container_book_details, containerFragment).commit()
        }
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById<TabLayout>(R.id.tabs_book_details)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
    }
}
