package com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback

import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Popular_species.Horizontal.PSModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Section.Vertical.SModel

interface ITabFragmentClickCallback {
    fun bookEventButtonClicked(book: BModel)
    fun sectionEventButtonClicked(section: SModel)
    fun popularSpeciesEventButtonClicked(pspecies: PSModel)
}