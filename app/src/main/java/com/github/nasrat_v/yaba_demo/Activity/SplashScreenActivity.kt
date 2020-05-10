package com.github.nasrat_v.yaba_demo.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.github.nasrat_v.yaba_demo.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mFadeInLogoAnim: Animation
    private lateinit var mFadeInTitleAnim: Animation
    private lateinit var mFadeInTitleBisAnim: Animation
    private lateinit var mLogo: ImageView
    private lateinit var mTitle: TextView
    private lateinit var mTitleBis: TextView
    private var mDelayHandler: Handler? = null

    companion object {
        const val SPLASH_DELAY: Long = 1000
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            val intent = Intent(applicationContext, RecommendedActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        initAnimations()
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    override fun onDestroy() {
        if (mDelayHandler != null)
            mDelayHandler!!.removeCallbacks(mRunnable)

        super.onDestroy()
    }

    private fun initAnimations() {
        mLogo = findViewById(R.id.logo_splash_screen)
        mTitle = findViewById(R.id.title_splash_screen)
        mTitleBis = findViewById(R.id.title_bis_splash_screen)

        initFadeInLogoAnimation()
        initFadeInTitleAnimation()
        initFadeInTitleBisAnimation()
        mLogo.startAnimation(mFadeInLogoAnim)
        mTitle.startAnimation(mFadeInTitleAnim)
        mTitleBis.startAnimation(mFadeInTitleBisAnim)
    }

    private fun initFadeInLogoAnimation() {
        mFadeInLogoAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash_screen)
        mFadeInLogoAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                mLogo.visibility = View.VISIBLE
            }
        })
    }

    private fun initFadeInTitleAnimation() {
        mFadeInTitleAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash_screen)
        mFadeInTitleAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                mTitle.visibility = View.VISIBLE
            }
        })
    }

    private fun initFadeInTitleBisAnimation() {
        mFadeInTitleBisAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash_screen)
        mFadeInTitleBisAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                mTitleBis.visibility = View.VISIBLE
            }
        })
    }
}