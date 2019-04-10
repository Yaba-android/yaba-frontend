package com.github.nasrat_v.maktaba_demo.AsyncTask

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_demo.Activity.SectionActivity
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_demo.Services.Provider.Genre.GModelProvider

class SectionNoTitleListBModelAsynFetchData(
    context: Context,
    private var selectedSection: GModel,
    private var languageCode: String
) :
    AsyncTaskLoader<ArrayList<NoTitleListBModel>>(context) {

    override fun loadInBackground(): ArrayList<NoTitleListBModel>? {
        //android.os.Debug.waitForDebugger()

        return GModelProvider(context, languageCode).getListAllBooksFromGenre(
            SectionActivity.NB_BOOKS_PER_ROW, selectedSection
        )
    }
}