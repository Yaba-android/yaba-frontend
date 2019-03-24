package com.github.nasrat_v.maktaba_android_frontend_mvp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask.SectionsGModelAsynFetchData
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.ISectionAdditionalClickCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.BottomOffsetDecoration
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.Vertical.GSRecyclerViewAdapter
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

@SuppressLint("Registered")
class SectionsActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<ArrayList<GModel>>,
    ISectionAdditionalClickCallback {

    private lateinit var mAdapterBookVertical: GSRecyclerViewAdapter
    private val mGenreList = arrayListOf<GModel>()
    private var mFirstInit = true

    companion object {
        const val ACTIVITY_NAME = "Sections"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections)

        mFirstInit = true
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

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<ArrayList<GModel>> {
        return SectionsGModelAsynFetchData(this)
    }

    override fun onLoadFinished(p0: Loader<ArrayList<GModel>>, data: ArrayList<GModel>?) {
        mGenreList.clear()
        mGenreList.addAll(data!!)
        mAdapterBookVertical.notifyDataSetChanged()
    }

    override fun onLoaderReset(p0: Loader<ArrayList<GModel>>) {
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onBackPressed() {
       returnToHome()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun sectionEventButtonClicked(genre: GModel) {
        val intent = Intent(this, SectionActivity::class.java)

        intent.putExtra(RecommendedActivity.SELECTED_POPULAR_SPECIES, genre)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun returnToHome() {
        val intent = Intent(this, RecommendedActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra(RecommendedActivity.LEFT_OR_RIGHT_IN_ANIMATION, 0)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun setListenerButtonCloseSection() {
        val button = findViewById<Button>(R.id.button_close_section)

        button.setOnClickListener {
            returnToHome()
        }
    }

    private fun initSectionNavVerticalRecycler() {
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_recyclerview_section)
        val linearLayout = findViewById<LinearLayout>(R.id.root_linear_layout_section)

        mAdapterBookVertical = GSRecyclerViewAdapter(this, mGenreList, this)
        verticalRecyclerView.setHasFixedSize(true)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.adapter = mAdapterBookVertical
        verticalRecyclerView.addItemDecoration(
            BottomOffsetDecoration(this, R.dimen.bottom_section_vertical_recycler_view)
        )
        verticalRecyclerView.isFocusable = false
        linearLayout.requestFocus()
    }
}