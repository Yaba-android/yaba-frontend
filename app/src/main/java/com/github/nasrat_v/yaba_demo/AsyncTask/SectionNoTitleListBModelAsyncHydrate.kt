package com.github.nasrat_v.yaba_demo.AsyncTask

import android.content.Context
import com.github.nasrat_v.yaba_demo.Activity.SectionActivity
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.yaba_demo.Services.Provider.Genre.GModelProvider

class SectionNoTitleListBModelAsyncHydrate(
    context: Context,
    private var selectedSection: GModel,
    private var languageCode: String
) :
    androidx.loader.content.AsyncTaskLoader<ArrayList<NoTitleListBModel>>(context) {

    override fun loadInBackground(): ArrayList<NoTitleListBModel>? {
        //android.os.Debug.waitForDebugger()

        return GModelProvider(context, languageCode).getListAllBooksFromGenre(
            SectionActivity.NB_BOOKS_PER_ROW, selectedSection
        )
    }
}