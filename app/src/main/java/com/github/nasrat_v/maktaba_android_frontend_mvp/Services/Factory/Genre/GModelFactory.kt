package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Factory.Genre

import android.content.Context
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.R

class GModelFactory() {

    fun getEmptyInstance(): GModel {
        return GModel("", 0, 0)
    }
}