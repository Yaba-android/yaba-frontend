package com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Model.BookDetailsBRModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Review.Vertical.RModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Review.RModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.BookDetailsContainerFragment

class BookDetailsBRModelAsyncFetchData(
    context: Context,
    private var languageCode: String
) :
    AsyncTaskLoader<BookDetailsBRModel>(context) {

    override fun loadInBackground(): BookDetailsBRModel? {
        //android.os.Debug.waitForDebugger()

        val allBooksFromDatabase = fetchAllBooksFromDatabase()
        val datasetBooks = arrayListOf<NoTitleListBModel>()
        val datasetReviews = arrayListOf<RModel>()

        mockDatasetBook(datasetBooks, allBooksFromDatabase)
        fetchDatasetReviewVerticalRecyclerView(datasetReviews)

        return BookDetailsBRModel(
            datasetBooks,
            datasetReviews
        )
    }

    private fun fetchAllBooksFromDatabase(): ArrayList<BModel> {
        return BModelProvider(context, languageCode).getAllBooksFromDatabase()
    }

    private fun fetchDatasetReviewVerticalRecyclerView(dataset: ArrayList<RModel>) {
        dataset.addAll(RModelProvider(context).getAllReviews())
    }

    private fun mockDatasetBook(dataset: ArrayList<NoTitleListBModel>, allBooksFromDatabase: ArrayList<BModel>) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                BookDetailsContainerFragment.RECYCLER_VIEW_NB_COLUMNS,
                BookDetailsContainerFragment.RECYCLER_VIEW_NB_BOOKS_PER_ROW,
                allBooksFromDatabase
            )
        )
    }

}