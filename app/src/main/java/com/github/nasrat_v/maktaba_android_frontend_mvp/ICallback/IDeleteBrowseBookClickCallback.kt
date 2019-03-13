package com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel

interface IDeleteBrowseBookClickCallback {
    fun bookEraseEventButtonClicked(book: BModel)
}