package com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment

import android.content.Context
import android.graphics.Typeface
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import org.readium.r2.streamer.server.Ressources

class TabLayoutCustomListener(var context: Context) {

    fun setListenerTabLayout(tabLayout: TabLayout) {
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                setTabTextToBold(tabLayout, tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                setTabTextToNormal(tabLayout, tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    fun setTabTextToBold(tabLayout: TabLayout, indexTab: Int) {
        val linearLayout = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(indexTab) as LinearLayout
        val tabTextView = linearLayout.getChildAt(1) as TextView
        val typeface = ResourcesCompat.getFont(context, R.font.noto_sans_family)

        tabTextView.setTypeface(typeface, Typeface.BOLD)
        tabTextView.setTextColor(ContextCompat.getColor(context, R.color.colorReviewTextBlack))
        tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    }

    fun setTabTextToNormal(tabLayout: TabLayout, indexTab: Int) {
        val linearLayout = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(indexTab) as LinearLayout
        val tabTextView = linearLayout.getChildAt(1) as TextView
        val typeface = ResourcesCompat.getFont(context, R.font.noto_sans_family)

        tabTextView.setTypeface(typeface, Typeface.NORMAL)
        tabTextView.setTextColor(ContextCompat.getColor(context, R.color.colorTextGrey))
        tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    }
}