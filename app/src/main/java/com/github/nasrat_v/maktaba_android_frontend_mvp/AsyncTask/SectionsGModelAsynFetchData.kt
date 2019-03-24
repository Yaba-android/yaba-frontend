package com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider

class SectionsGModelAsynFetchData(
    context: Context
) :
    AsyncTaskLoader<ArrayList<GModel>>(context) {

    override fun loadInBackground(): ArrayList<GModel>? {
        //android.os.Debug.waitForDebugger()

        return GModelProvider(context).getAllGenres()
    }
}