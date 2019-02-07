package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CardViewAdapterExemple(var models: List<Model>, var context: Context) : PagerAdapter() {

    lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return models.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.item_card_view_exemple, container, false)

        val imageView: ImageView
        val title: TextView
        val desc: TextView

        imageView = view.findViewById(R.id.image_item)
        title = view.findViewById(R.id.title_item)
        desc = view.findViewById(R.id.description_item)

        imageView.setImageResource(models[position].image)
        title.text = models[position].title
        desc.text = models[position].desc

        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }
}