package com.github.nasrat_v.yaba_android.AsyncHydrater

import android.content.Context
import com.github.nasrat_v.yaba_android.Activity.SectionActivity
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_android.Listable.Genre.GModel
import com.github.nasrat_v.yaba_android.Services.Provider.Genre.GModelProvider

class SectionNoTitleListBModelAsyncHydrater(
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