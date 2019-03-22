package com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel

interface ISectionAdditionalClickCallback {
    fun sectionEventButtonClicked(genre: GModel)
}