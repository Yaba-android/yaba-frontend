package com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.LibraryActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel

class LibraryBModelAsyncFetchData(
    context: Context,
    private var allBooksFromDatabase: ArrayList<BModel>
) :
    AsyncTaskLoader<LibraryBModel>(context) {

    override fun loadInBackground(): LibraryBModel? {
        //android.os.Debug.waitForDebugger()
        val allbooksLibrary = arrayListOf<NoTitleListBModel>()
        val downloadsLibrary = arrayListOf<DownloadListBModel>()
        val groupsLibrary = arrayListOf<GroupListBModel>()

        mockDatasetAllBooks(allbooksLibrary)
        mockDatasetGroups(allbooksLibrary, groupsLibrary)
        mockDatasetDownload(allbooksLibrary, downloadsLibrary)
        return LibraryBModel(downloadsLibrary, groupsLibrary, allbooksLibrary)
    }

    fun deliverResult(data: LibraryBModel) {
        super.deliverResult(data)
    }

    private fun mockDatasetAllBooks(dataset: ArrayList<NoTitleListBModel>) {
        dataset.addAll(
            BModelRandomProvider(context).getRandomsInstancesFromListToNoTitleListBModel(
                LibraryActivity.ALLBOOKS_NB_BOOK_COLUMNS,
                LibraryActivity.ALLBOOKS_NB_BOOK_PER_ROW,
                allBooksFromDatabase
            )
        )
    }

    private fun removeIndexFromTmpList(selectedIndex: ArrayList<Int>, tmpList: ArrayList<BModel>) {
        selectedIndex.forEach {
            tmpList.removeAt(it)
        }
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