package com.github.nasrat_v.yaba_android.ICallback

import com.github.nasrat_v.yaba_android.Listable.Genre.GModel

interface IGModelProviderCallback {
    fun onGetAllGenresRequestSuccess(genres: ArrayList<GModel>)
}