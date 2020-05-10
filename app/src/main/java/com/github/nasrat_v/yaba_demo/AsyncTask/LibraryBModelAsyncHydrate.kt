package com.github.nasrat_v.yaba_demo.AsyncTask

import android.content.Context
import com.github.nasrat_v.yaba_demo.Activity.LibraryActivity
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.Model.LibraryBModel
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.LibraryBModelProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.LibraryBModelRandomProvider

class LibraryBModelAsyncHydrate(
    context: Context,
    private var languageCode: String,
    private var allBooks: ArrayList<BModel>
) :
    androidx.loader.content.AsyncTaskLoader<LibraryBModel>(context) {

    override fun loadInBackground(): LibraryBModel? {
        //android.os.Debug.waitForDebugger()

        val allbooksLibrary = arrayListOf<NoTitleListBModel>()
        val downloadsLibrary = arrayListOf<DownloadListBModel>()
        val groupsLibrary = arrayListOf<GroupListBModel>()

        mockDatasetAllBooks(allbooksLibrary)
        hydrateDatasetGroups(allbooksLibrary, groupsLibrary)
        hydrateDatasetDownload(allbooksLibrary, downloadsLibrary)

        return LibraryBModel(
            downloadsLibrary,
            groupsLibrary,
            allbooksLibrary
        )
    }

    private fun fetchAllBooksFromResource(): ArrayList<BModel> {
        return BModelProvider(context, languageCode).getAllBooksFromResource()
    }


    private fun mockDatasetAllBooks(dataset: ArrayList<NoTitleListBModel>) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                LibraryActivity.ALLBOOKS_NB_BOOK_COLUMNS,
                LibraryActivity.ALLBOOKS_NB_BOOK_PER_ROW,
                allBooks
            )
        )
    }

    private fun hydrateDatasetGroups(
        allbooksLibrary: ArrayList<NoTitleListBModel>,
        dataset: ArrayList<GroupListBModel>
    ) {
        dataset.addAll(
            LibraryBModelProvider().getGroupListFromList(
                LibraryActivity.GROUPS_NB_GROUP_PER_ROW,
                allbooksLibrary
            )
        )
    }

    private fun hydrateDatasetDownload(
        allbooksLibrary: ArrayList<NoTitleListBModel>,
        dataset: ArrayList<DownloadListBModel>
    ) {
        dataset.addAll(
            LibraryBModelProvider().getDownloadedListBookFromList(
                LibraryActivity.DOWNLOAD_NB_BOOK_PER_ROW,
                allbooksLibrary
            )
        )
    }
}