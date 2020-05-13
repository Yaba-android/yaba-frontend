package com.github.nasrat_v.yaba_android.ICallback

import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.BModel

interface IBModelProviderCallback {
    fun onGetAllBooksRequestSuccess(allBooks: ArrayList<BModel>)
    fun onGetBookRequestSuccess(book: BModel)
}