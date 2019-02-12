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
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.Menu
import android.widget.Button

class MainActivity : AppCompatActivity(),
    ITabFragmentClickCallback, ITabLayoutSetupCallback, MainContainerFragment.AdditionalClickCallback {

    private lateinit var mDrawerLayout: DrawerLayout

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_application)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        mDrawerLayout = findViewById(R.id.drawer_main)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        val mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
            R.string.navigation_drawer_profile_open, R.string.navigation_drawer_profile_close)
        mDrawerToggle.syncState()
        mDrawerLayout.setDrawerListener(mDrawerToggle)

        val containerFragment = MainContainerFragment()
        containerFragment.setTabFragmentClickCallback(this) // permet de gerer les click depuis les fragments
        containerFragment.setAdditionalClickCallback(this) // click additionnel uniquement pour le fragment browse

        val buttonCloseGenre = findViewById<Button>(R.id.button_close_nav_genre)
        buttonCloseGenre.setOnClickListener {
            genreNavigationEventButtonClicked()
        }

        if (savedInstanceState == null) {
            val mFragmentManager = supportFragmentManager
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val mFragmentTransaction = mFragmentManager.beginTransaction()
            mFragmentTransaction.replace(R.id.fragment_container_main, containerFragment).commit()
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

    override fun setupTabLayout(viewPager: ViewPager) {
        val tabLayout = findViewById<TabLayout>(R.id.tabs_main)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun genreNavigationEventButtonClicked() {
        if (mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.closeDrawer(Gravity.END)
        else
            mDrawerLayout.openDrawer(Gravity.END)
    }

    override fun bookEventButtonClicked() {
        val intent = Intent(this, BookDetailsActivity::class.java)

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}