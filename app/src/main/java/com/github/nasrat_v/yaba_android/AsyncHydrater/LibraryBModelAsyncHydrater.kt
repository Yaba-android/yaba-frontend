package com.github.nasrat_v.yaba_android.AsyncHydrater

import android.content.Context
import com.github.nasrat_v.yaba_android.Activity.LibraryActivity
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.Model.LibraryBModel
import com.github.nasrat_v.yaba_android.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.yaba_android.Services.Provider.Book.LibraryBModelProvider

class LibraryBModelAsyncHydrater(
    context: Context,
    private var languageCode: String,
    private var booksLibrary: ArrayList<BModel>
) :
    androidx.loader.content.AsyncTaskLoader<LibraryBModel>(context) {

    override fun loadInBackground(): LibraryBModel? {
        //android.os.Debug.waitForDebugger()

        val allBooksLibrary = arrayListOf<NoTitleListBModel>()
        val downloadsLibrary = arrayListOf<DownloadListBModel>()
        val groupsLibrary = arrayListOf<GroupListBModel>()

        hydrateDatasetAllBooks(allBooksLibrary)
        hydrateDatasetGroups(groupsLibrary, allBooksLibrary)
        hydrateDatasetDownload(downloadsLibrary, allBooksLibrary)

        return LibraryBModel(
            downloadsLibrary,
            groupsLibrary,
            allBooksLibrary
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

    private fun hydrateDatasetAllBooks(dataset: ArrayList<NoTitleListBModel>) {
        dataset.addAll(
            LibraryBModelProvider(context, languageCode).getAllBooksListBookFromList(
                LibraryActivity.ALLBOOKS_NB_BOOK_PER_ROW,
                booksLibrary
            )
        )
    }

    private fun hydrateDatasetGroups(
        dataset: ArrayList<GroupListBModel>,
        allBooks: ArrayList<NoTitleListBModel>
    ) {
        dataset.addAll(
            LibraryBModelProvider(context, languageCode).getGroupListFromList(
                LibraryActivity.GROUPS_NB_GROUP_PER_ROW,
                allBooks
            )
        )
    }

    private fun hydrateDatasetDownload(
        dataset: ArrayList<DownloadListBModel>,
        allBooks: ArrayList<NoTitleListBModel>
    ) {
        dataset.addAll(
            LibraryBModelProvider(context, languageCode).getDownloadedListBookFromList(
                LibraryActivity.DOWNLOAD_NB_BOOK_PER_ROW,
                allBooks
            )
        )
    }
}