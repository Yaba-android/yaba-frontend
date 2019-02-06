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

class MainActivity : AppCompatActivity(), ClickInterface {

    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mNavigationViewProfile: NavigationView
    lateinit var mNavigationViewGenre: NavigationView
    lateinit var mFragmentManager: FragmentManager
    lateinit var mFragmentTransaction: FragmentTransaction

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById<View>(R.id.drawer_layout_profile) as DrawerLayout

        val tabFragment = TabFragment()
        tabFragment.setClickInterface(this) // permet de gerer les click depuis le fragment

        mFragmentManager = supportFragmentManager
        mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.fragment_container_profile, tabFragment).commit()

        /*mNavigationView.setNavigationItemSelectedListener {
            menuItem -> mDrawerLayout.closeDrawers()

            if (menuItem.itemId == R.id.nav_profile) {

            }
        }*/

        val toolbar = findViewById<View>(R.id.toolbar_profile) as Toolbar
        val mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout,
            toolbar, R.string.app_name, R.string.app_name)
        mDrawerLayout.setDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
    }

    override fun buttonClicked() {
            if (mDrawerLayout.isDrawerOpen(Gravity.END))
                mDrawerLayout.closeDrawer(Gravity.END)
            else
                mDrawerLayout.openDrawer(Gravity.END)
    }
}