package com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback

import com.github.nasrat_v.maktaba_android_frontend_mvp.Genre.GModel

interface IRecommendedAdditionalClickCallback {
    fun sectionEventButtonClicked(section: GModel)
    fun popularSpeciesEventButtonClicked(pspecies: GModel)
}