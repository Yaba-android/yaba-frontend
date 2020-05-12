package com.github.nasrat_v.yaba_demo.AsyncHydrater

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

class LibraryBModelAsyncHydrater(
    context: Context,
    private var languageCode: String,
    private var allbooksLibrary: ArrayList<NoTitleListBModel>
) :
    androidx.loader.content.AsyncTaskLoader<LibraryBModel>(context) {

    override fun loadInBackground(): LibraryBModel? {
        //android.os.Debug.waitForDebugger()

        val downloadsLibrary = arrayListOf<DownloadListBModel>()
        val groupsLibrary = arrayListOf<GroupListBModel>()

        hydrateDatasetGroups(groupsLibrary)
        hydrateDatasetDownload(downloadsLibrary)

        return LibraryBModel(
            downloadsLibrary,
            groupsLibrary,
            allbooksLibrary
        )
    }

    private fun fetchAllBooksFromResource(): ArrayList<BModel> {
        return BModelProvider(context, languageCode).getAllBooksFromResource()
    }

    /*private fun mockDatasetAllBooks(dataset: ArrayList<NoTitleListBModel>) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                LibraryActivity.ALLBOOKS_NB_BOOK_COLUMNS,
                LibraryActivity.ALLBOOKS_NB_BOOK_PER_ROW,
                allBooks
            )
        )
    }*/

    private fun hydrateDatasetGroups(
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