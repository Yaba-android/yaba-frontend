package com.github.nasrat_v.maktaba_demo.ICallback

import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.BModel

interface IBookInfosProvider {
    fun getSelectedBook() : BModel
}