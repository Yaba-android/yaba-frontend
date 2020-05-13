package com.github.nasrat_v.yaba_demo.ICallback

import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel

interface IGModelProviderCallback {
    fun onGetAllGenresRequestSuccess(genres: ArrayList<GModel>)
}