package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.LibraryActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.Model.LibraryBModel

class LibraryBModelAsyncFetchData(context: Context) : AsyncTaskLoader<LibraryBModel>(context) {

    override fun loadInBackground(): LibraryBModel? {
        val allbooks = arrayListOf<NoTitleListBModel>()
        val downloads = arrayListOf<DownloadListBModel>()
        val groups = arrayListOf<GroupListBModel>()

        mockDatasetAllBooks(allbooks)
        mockDatasetGroups(allbooks, groups)
        mockDatasetDownload(allbooks, downloads)
        return LibraryBModel(downloads, groups, allbooks)
    }

    fun deliverResult(data: LibraryBModel) {
        super.deliverResult(data)
    }

    private fun mockDatasetAllBooks(dataset: ArrayList<NoTitleListBModel>) {
        val factory = BModelRandomProvider(context)

        for (index in 0..(LibraryActivity.NB_ALL_BOOKS - 1)) {
            dataset.add(
                NoTitleListBModel(
                    factory.getRandomsInstances(LibraryActivity.ALLBOOKS_NB_BOOK_PER_ROW)
                )
            )
        }
    }

    private fun mockDatasetGroups(
        allbooksDataset: ArrayList<NoTitleListBModel>,
        dataset: ArrayList<GroupListBModel>
    ) {
        dataset.addAll(
            LibraryBModelProvider().getGroupListFromList(
                LibraryActivity.GROUPS_NB_GROUP_PER_ROW,
                allbooksDataset
            )
        )
    }

    private fun mockDatasetDownload(
        allbooksDataset: ArrayList<NoTitleListBModel>,
        dataset: ArrayList<DownloadListBModel>
    ) {
        dataset.addAll(
            LibraryBModelRandomProvider().getRandomDownloadedListBookFromList(
                3,
                LibraryActivity.DOWNLOAD_NB_BOOK_PER_ROW,
                allbooksDataset
            )
        )
    }
}