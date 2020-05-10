package com.github.nasrat_v.yaba_demo.ICallback

import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel

interface ISectionAdditionalClickCallback {
    fun sectionEventButtonClicked(genre: GModel)
}