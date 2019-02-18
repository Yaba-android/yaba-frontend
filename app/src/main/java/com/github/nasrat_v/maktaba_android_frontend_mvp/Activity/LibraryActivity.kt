package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import com.folioreader.FolioReader
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class LibraryActivity : AppCompatActivity() {

    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_library)

        setListenerButtonFooter()
        setListenerButtonFolioReader()
    }

    private fun setListenerButtonFooter() {
        val intent = Intent(this, StoreActivity::class.java)
        val button = findViewById<Button>(R.id.button_store_footer)

        button.setOnClickListener {
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                finish()
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }

    private fun setListenerButtonFolioReader() {
        val folioReader = FolioReader.get()
        val openEpubReader = findViewById<Button>(R.id.open_epub_reader)

        openEpubReader.setOnClickListener {
            if ((SystemClock.elapsedRealtime() - mLastClickTime) >= 1000) { // Prevent double click
                folioReader.openBook("/storage/emulated/0/Download/pg58892-images.epub")
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
    }
}