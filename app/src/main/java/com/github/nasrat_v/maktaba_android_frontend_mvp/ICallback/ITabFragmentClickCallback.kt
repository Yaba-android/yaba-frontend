package com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback

import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.BModel

interface ITabFragmentClickCallback {
    fun bookEventButtonClicked(book: BModel)
}