package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager

class MainActivity : AppCompatActivity(),
    ContainerFragment.ClickCallback, ContainerFragment.TabLayoutSetupCallback {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mFragmentTransaction: FragmentTransaction

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_profile)
        setSupportActionBar(toolbar)

        mDrawerLayout = findViewById(R.id.drawer_layout_profile)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        val mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
            R.string.navigation_drawer_profile_open, R.string.navigation_drawer_profile_close)
        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)

        val containerFragment = ContainerFragment()
        containerFragment.setClickCallback(this) // permet de gerer les click depuis le fragment

        if (savedInstanceState == null) {
            mFragmentManager = supportFragmentManager
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            mFragmentTransaction = mFragmentManager.beginTransaction()
            mFragmentTransaction.replace(R.id.fragment_container_profile, containerFragment).commit()
        }
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
    }

    override fun genreNavigationEventButtonClicked() {
        if (mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.closeDrawer(Gravity.END)
        else
            mDrawerLayout.openDrawer(Gravity.END)
    }

    override fun bookEventButtonClicked() {
        val intent = Intent(this, BookDetails::class.java)

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}