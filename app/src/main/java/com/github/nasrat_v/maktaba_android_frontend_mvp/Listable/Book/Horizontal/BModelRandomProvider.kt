package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import kotlin.collections.ArrayList

class BModelRandomProvider(private var context: Context) {

    fun getRandomsInstances(nb: Int) : ArrayList<BModel> {
        val randomFactory = BModelRandomFactory(context)
        val listModel = arrayListOf<BModel>()

        for (index in 0..(nb - 1)) {
            listModel.add(randomFactory.getRandomInstance())
        }
        return listModel
    }

    fun getRandomsInstancesDiscreteScrollView(nb: Int) : ArrayList<BModel> {
        val randomFactory = BModelRandomFactory(context)
        val listModel = arrayListOf<BModel>()

        for (index in 0..(nb - 1)) {
            listModel.add(randomFactory.getRandomInstanceDiscreteScrollView())
        }
        return listModel
    }
}