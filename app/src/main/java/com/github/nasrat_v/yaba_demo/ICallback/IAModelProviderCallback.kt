package com.github.nasrat_v.yaba_demo.ICallback

import com.github.nasrat_v.yaba_demo.Listable.Author.AModel

interface IAModelProviderCallback {
    fun onGetAllAuthorsRequestSuccess(authors: ArrayList<AModel>)
    fun onGetAuthorRequestSuccess(author: AModel)
}