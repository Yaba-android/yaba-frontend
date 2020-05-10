package com.github.nasrat_v.yaba_demo.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.github.nasrat_v.yaba_demo.AsyncTask.SectionsGModelAsyncHydrate
import com.github.nasrat_v.yaba_demo.ICallback.ISectionAdditionalClickCallback
import com.github.nasrat_v.yaba_demo.Language.StringLocaleResolver
import com.github.nasrat_v.yaba_demo.Listable.BottomOffsetDecoration
import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.yaba_demo.Listable.Genre.Vertical.GSRecyclerViewAdapter
import com.github.nasrat_v.yaba_demo.R

@SuppressLint("Registered")
class SectionsActivity : AppCompatActivity(),
    androidx.loader.app.LoaderManager.LoaderCallbacks<ArrayList<GModel>>,
    ISectionAdditionalClickCallback {

    private lateinit var mAdapterBookVertical: GSRecyclerViewAdapter
    private lateinit var mProgressBar: ProgressBar
    private val mGenreList = arrayListOf<GModel>()
    private var mLanguage = StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    private var mFirstInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections)

        localeOnNewIntent()

        mProgressBar = findViewById(R.id.progress_bar_sections)
        mFirstInit = true

        initTitle()
        initSectionNavVerticalRecycler()
    }

    override fun onStart() {
        super.onStart()

        if (mFirstInit) {
            setListenerButtonCloseSection()

            supportLoaderManager.initLoader(0, null, this).forceLoad() // init GModel in async task
        }
        mFirstInit = false
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): androidx.loader.content.Loader<ArrayList<GModel>> {
        return SectionsGModelAsyncHydrate(this, mLanguage)
    }

    override fun onLoadFinished(p0: androidx.loader.content.Loader<ArrayList<GModel>>, data: ArrayList<GModel>?) {
        mGenreList.clear()
        mGenreList.addAll(data!!)
        mAdapterBookVertical.notifyDataSetChanged()
        mProgressBar.visibility = View.GONE
    }

    override fun onLoaderReset(p0: androidx.loader.content.Loader<ArrayList<GModel>>) {
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onBackPressed() {
       returnToHome()
    }

    override fun sectionEventButtonClicked(genre: GModel) {
        val intent = Intent(this, SectionActivity::class.java)

        intent.putExtra(RecommendedActivity.SELECTED_POPULAR_SPECIES, genre)
        startNewActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setListenerButtonCloseSection() {
        val button = findViewById<Button>(R.id.button_close_section)

        button.setOnClickListener {
            returnToHome()
        }
    }

    private fun startNewActivity(intent: Intent) {
        intent.putExtra(StringLocaleResolver.LANGUAGE_CODE, mLanguage)
        startActivity(intent)
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)
        startNewActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    private fun localeOnNewIntent() {
        mLanguage =
            intent.getStringExtra(StringLocaleResolver.LANGUAGE_CODE) ?: StringLocaleResolver.DEFAULT_LANGUAGE_CODE
    }

    private fun initTitle() {
        val title = findViewById<TextView>(R.id.genre_title)

        title.text = getString(StringLocaleResolver(mLanguage).getRes(R.string.sections))
    }

    private fun initSectionNavVerticalRecycler() {
        val verticalRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.vertical_recyclerview_section)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_section)

        mAdapterBookVertical = GSRecyclerViewAdapter(this, mGenreList, this)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_section_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}