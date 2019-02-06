package com.github.nasrat_v.maktaba_android_frontend_mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_tabbed_browse.view.*

class BrowseTabbedFragment : Fragment() {

    lateinit var mClickInterface: ClickInterface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tabbed_browse, container, false)
    }

    fun setClickInterface(cInterface: ClickInterface) {
        mClickInterface = cInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonGenre = getView()!!.findViewById<Button>(R.id.button_genre_nav)

        buttonGenre.setOnClickListener {
            mClickInterface.buttonClicked() // l'event click est envoyé à l'activity parent grâce à l'interface
        }
    }
}