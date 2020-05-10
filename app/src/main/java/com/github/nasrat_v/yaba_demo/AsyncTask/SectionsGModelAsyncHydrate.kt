package com.github.nasrat_v.yaba_demo.AsyncTask

import android.content.Context
import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.yaba_demo.Services.Provider.Genre.GModelProvider

class SectionsGModelAsyncHydrate(
    context: Context,
    private var languageCode: String
) :
    androidx.loader.content.AsyncTaskLoader<ArrayList<GModel>>(context) {

    override fun loadInBackground(): ArrayList<GModel>? {
        //android.os.Debug.waitForDebugger()

        return GModelProvider(context, languageCode).getAllGenres()
    }
}