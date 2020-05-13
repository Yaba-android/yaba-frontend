package com.github.nasrat_v.yaba_android.ICallback

import com.github.nasrat_v.yaba_android.Listable.Author.AModel

interface IAModelProviderCallback {
    fun onGetAllAuthorsRequestSuccess(authors: ArrayList<AModel>)
    fun onGetAuthorRequestSuccess(author: AModel)
}