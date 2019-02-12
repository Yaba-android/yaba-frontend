package com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback

import com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal.Model

interface ITabFragmentClickCallback {
    fun bookEventButtonClicked(book: Model)
}