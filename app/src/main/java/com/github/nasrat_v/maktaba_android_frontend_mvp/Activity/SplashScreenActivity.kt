package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mFadeInAnim: Animation
    private lateinit var mFadeOutAnim: Animation
    private lateinit var mTitle: TextView
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
        mTitle = findViewById(R.id.title_splash_screen)
        initFadeInAnimation()
        initFadeOutAnimation()
        mTitle.startAnimation(mFadeInAnim)
    }

    private fun initFadeInAnimation() {
        mFadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        mFadeInAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                mTitle.startAnimation(mFadeOutAnim)
            }

            override fun onAnimationStart(animation: Animation?) {
                mTitle.visibility = View.VISIBLE
            }
        })
    }

    private fun initFadeOutAnimation() {
        mFadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        mFadeOutAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                mTitle.visibility = View.GONE
                mTitle.startAnimation(mFadeInAnim)
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
    }
}