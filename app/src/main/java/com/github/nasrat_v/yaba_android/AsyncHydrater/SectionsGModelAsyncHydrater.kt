package com.github.nasrat_v.yaba_android.AsyncHydrater

import android.content.Context
import com.github.nasrat_v.yaba_android.Listable.Genre.GModel
import com.github.nasrat_v.yaba_android.Services.Provider.Genre.GModelProvider

class SectionsGModelAsyncHydrater(
    context: Context,
    private var languageCode: String
) :
    androidx.loader.content.AsyncTaskLoader<ArrayList<GModel>>(context) {

    override fun loadInBackground(): ArrayList<GModel>? {
        //android.os.Debug.waitForDebugger()

        return GModelProvider(context, languageCode).getAllGenres()
    }
}