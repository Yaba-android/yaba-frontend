package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

@SuppressLint("Registered")
class BrowseActivity : AppCompatActivity() {

    companion object {
        const val LEFT_OR_RIGHT_IN_ANIMATION = "LeftOrRightInAnimation"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_browse)

        setListenerLibraryButtonFooter()
        setListenerRecommendedButtonFooter()
    }

    override fun onBackPressed() {
        returnToHome()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val anim = intent!!.getIntExtra(LEFT_OR_RIGHT_IN_ANIMATION, 0)

        if (anim == 0) // left
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        else if (anim == 1) // right
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra(LEFT_OR_RIGHT_IN_ANIMATION, 1)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setListenerRecommendedButtonFooter() {
        val button = findViewById<Button>(R.id.button_recommended_footer)

        button.setOnClickListener {
            returnToHome()
        }
    }

    private fun setListenerLibraryButtonFooter() {
        val intent = Intent(this, LibraryActivity::class.java)
        val button = findViewById<Button>(R.id.button_library_footer)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        button.setOnClickListener {
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}