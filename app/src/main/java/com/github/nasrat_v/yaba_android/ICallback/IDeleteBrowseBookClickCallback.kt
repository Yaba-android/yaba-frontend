package com.github.nasrat_v.yaba_android.ICallback

import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.BModel

interface IDeleteBrowseBookClickCallback {
    fun bookEraseEventButtonClicked(book: BModel, position: Int)
    fun recyclerViewEraseEventButtonClicked(position: Int)
}