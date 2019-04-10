package com.github.nasrat_v.maktaba_demo.ICallback

import com.github.nasrat_v.maktaba_demo.Listable.Genre.GModel

interface IRecommendedAdditionalClickCallback {
    fun popularSpeciesEventButtonClicked(pspecies: GModel)
}