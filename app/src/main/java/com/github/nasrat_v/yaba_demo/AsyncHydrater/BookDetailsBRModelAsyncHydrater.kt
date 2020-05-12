package com.github.nasrat_v.yaba_demo.AsyncHydrater

import android.content.Context
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.Listable.Model.BookDetailsBRModel
import com.github.nasrat_v.yaba_demo.Listable.Review.Vertical.RModel
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.Review.RModelProvider
import com.github.nasrat_v.yaba_demo.TabFragment.BookDetailsContainerFragment

class BookDetailsBRModelAsyncHydrater(
    context: Context,
    private var languageCode: String,
    private var authorBooks: ArrayList<BModel>
) :
    androidx.loader.content.AsyncTaskLoader<BookDetailsBRModel>(context) {

    override fun loadInBackground(): BookDetailsBRModel? {
        //android.os.Debug.waitForDebugger()

        val datasetBooks = arrayListOf<NoTitleListBModel>()
        val datasetReviews = arrayListOf<RModel>()

        hydrateDatasetBook(datasetBooks)
        fetchDatasetReviewVerticalRecyclerView(datasetReviews)

        return BookDetailsBRModel(
            datasetBooks,
            datasetReviews
        )
    }

    private fun fetchAllBooksFromResource(): ArrayList<BModel> {
        return BModelProvider(context, languageCode).getAllBooksFromResource()
    }

    private fun fetchDatasetReviewVerticalRecyclerView(dataset: ArrayList<RModel>) {
        dataset.addAll(RModelProvider(context).getAllReviews())
    }

    private fun hydrateDatasetBook(dataset: ArrayList<NoTitleListBModel>) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                BookDetailsContainerFragment.RECYCLER_VIEW_NB_COLUMNS,
                BookDetailsContainerFragment.RECYCLER_VIEW_NB_BOOKS_PER_ROW,
                authorBooks
            )
        )
    }

}