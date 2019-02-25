package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

@SuppressLint("Registered")
class BrowseActivity : AppCompatActivity() {

    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_browse)

        setListenerLibraryButtonFooter()
        setListenerRecommendedButtonFooter()
    }

    private fun setListenerRecommendedButtonFooter() {
        val intent = Intent(this, RecommendedActivity::class.java)
        val buttonBrowse = findViewById<Button>(R.id.button_recommended_footer)

        buttonBrowse.setOnClickListener {
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    private fun setListenerLibraryButtonFooter() {
        val intent = Intent(this, LibraryActivity::class.java)
        val buttonRecommended = findViewById<Button>(R.id.button_library_footer)


        buttonRecommended.setOnClickListener {
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }
}