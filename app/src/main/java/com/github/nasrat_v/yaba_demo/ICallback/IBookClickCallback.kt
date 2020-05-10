package com.github.nasrat_v.yaba_demo.ICallback

import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel

interface IBookClickCallback {
    fun bookEventButtonClicked(book: BModel)
}