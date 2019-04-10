package com.github.nasrat_v.maktaba_demo.AsyncTask

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_demo.Services.Provider.Genre.GModelProvider

class SectionsGModelAsynFetchData(
    context: Context,
    private var languageCode: String
) :
    AsyncTaskLoader<ArrayList<GModel>>(context) {

    override fun loadInBackground(): ArrayList<GModel>? {
        //android.os.Debug.waitForDebugger()

        return GModelProvider(context, languageCode).getAllGenres()
    }
}