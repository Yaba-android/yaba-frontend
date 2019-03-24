package com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.SectionActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider

class SectionNoTitleListBModelAsynFetchData(
    context: Context,
    private var selectedSection: GModel
) :
    AsyncTaskLoader<ArrayList<NoTitleListBModel>>(context) {

    override fun loadInBackground(): ArrayList<NoTitleListBModel>? {
        //android.os.Debug.waitForDebugger()

        return GModelProvider(context)
            .getListAllBooksFromGenre(SectionActivity.NB_BOOKS_PER_ROW, selectedSection)
    }
}