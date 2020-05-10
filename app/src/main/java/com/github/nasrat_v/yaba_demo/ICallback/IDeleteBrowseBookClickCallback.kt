package com.github.nasrat_v.yaba_demo.ICallback

import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel

interface IDeleteBrowseBookClickCallback {
    fun bookEraseEventButtonClicked(book: BModel, position: Int)
    fun recyclerViewEraseEventButtonClicked(position: Int)
}