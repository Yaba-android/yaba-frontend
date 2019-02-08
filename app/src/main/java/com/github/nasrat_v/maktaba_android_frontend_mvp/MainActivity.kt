package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager

class MainActivity : AppCompatActivity(), ContainerFragment.GenreNavigationClickCallback, ContainerFragment.TabLayoutSetupCallback {

    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mNavigationViewProfile: NavigationView
    lateinit var mNavigationViewGenre: NavigationView
    lateinit var mFragmentManager: FragmentManager
    lateinit var mFragmentTransaction: FragmentTransaction

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar_profile) as Toolbar
        setSupportActionBar(toolbar)

        mDrawerLayout = findViewById<View>(R.id.drawer_layout_profile) as DrawerLayout

        val mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout,
            toolbar, R.string.navigation_drawer_profile_open, R.string.navigation_drawer_profile_close)

        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)

        val containerFragment = ContainerFragment()
        containerFragment.setGenreNavigationClickCallback(this) // permet de gerer les click depuis le fragment

        mFragmentManager = supportFragmentManager
        mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.fragment_container_profile, containerFragment).commit()
    }

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
    }

    override fun eventButtonClicked() {
        if (mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.closeDrawer(Gravity.END)
        else
            mDrawerLayout.openDrawer(Gravity.END)
    }
}