package com.github.nasrat_v.maktaba_demo.AsyncTask

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_demo.Activity.LibraryActivity
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.Model.LibraryBModel
import com.github.nasrat_v.maktaba_demo.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.maktaba_demo.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.maktaba_demo.Services.Provider.Book.LibraryBModelProvider
import com.github.nasrat_v.maktaba_demo.Services.Provider.Book.LibraryBModelRandomProvider

class LibraryBModelAsyncFetchData(
    context: Context,
    private var languageCode: String
) :
    androidx.loader.content.AsyncTaskLoader<LibraryBModel>(context) {

    override fun loadInBackground(): LibraryBModel? {
        //android.os.Debug.waitForDebugger()

        val allBooksFromDatabase = fetchAllBooksFromDatabase()
        val allbooksLibrary = arrayListOf<NoTitleListBModel>()
        val downloadsLibrary = arrayListOf<DownloadListBModel>()
        val groupsLibrary = arrayListOf<GroupListBModel>()

        mockDatasetAllBooks(allbooksLibrary, allBooksFromDatabase)
        mockDatasetGroups(allbooksLibrary, groupsLibrary)
        mockDatasetDownload(allbooksLibrary, downloadsLibrary)

        return LibraryBModel(
            downloadsLibrary,
            groupsLibrary,
            allbooksLibrary
        )
    }

    private fun fetchAllBooksFromDatabase(): ArrayList<BModel> {
        return BModelProvider(context, languageCode).getAllBooksFromDatabase()
    }


    private fun mockDatasetAllBooks(dataset: ArrayList<NoTitleListBModel>, allBooksFromDatabase: ArrayList<BModel>) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                LibraryActivity.ALLBOOKS_NB_BOOK_COLUMNS,
                LibraryActivity.ALLBOOKS_NB_BOOK_PER_ROW,
                allBooksFromDatabase
            )
        )
    }

    private fun mockDatasetGroups(
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

    private fun mockDatasetDownload(
        allbooksLibrary: ArrayList<NoTitleListBModel>,
        dataset: ArrayList<DownloadListBModel>
    ) {
        dataset.addAll(
            LibraryBModelRandomProvider().getRandomDownloadedListBookFromList(
                LibraryActivity.DOWNLOAD_NB_BOOK_COLUMNS,
                LibraryActivity.DOWNLOAD_NB_BOOK_PER_ROW,
                allbooksLibrary
            )
        )
    }
}