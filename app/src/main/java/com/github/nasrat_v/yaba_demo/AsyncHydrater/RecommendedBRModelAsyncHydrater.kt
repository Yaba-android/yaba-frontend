package com.github.nasrat_v.yaba_demo.AsyncHydrater

import android.content.Context
import com.github.nasrat_v.yaba_demo.Activity.RecommendedActivity
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.yaba_demo.Listable.Model.RecommendedBRModel
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.yaba_demo.Services.Provider.Genre.GModelProvider

class RecommendedBRModelAsyncHydrater(
    context: Context,
    private var languageCode: String,
    private var allBooks: ArrayList<BModel>
) :
    androidx.loader.content.AsyncTaskLoader<RecommendedBRModel>(context) {

    override fun loadInBackground(): RecommendedBRModel? {
        //android.os.Debug.waitForDebugger()

        val datasetCarousel = arrayListOf<BModel>()
        val datasetFirst = arrayListOf<NoTitleListBModel>()
        val datasetPopularGenre = arrayListOf<GModel>()
        val datasetSecond = arrayListOf<NoTitleListBModel>()
        val datasetSmall = arrayListOf<NoTitleListBModel>()

        mockDatasetCarousel(datasetCarousel)
        mockDatasetFirstRecyclerView(datasetFirst)
        mockDatasetPopularGenre(datasetPopularGenre)
        mockDatasetSecondRecyclerView(datasetSecond)
        mockDatasetSmallRecyclerView(datasetSmall)

        return RecommendedBRModel(
            datasetCarousel, datasetFirst,
            datasetPopularGenre,
            datasetSecond, datasetSmall
        )
    }

    private fun fetchAllBooksFromResource(): ArrayList<BModel> {
        return BModelProvider(context, languageCode).getAllBooksFromResource()
    }

    private fun mockDatasetCarousel(
        dataset: ArrayList<BModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromList(
                RecommendedActivity.NB_BOOKS_CAROUSEL,
                allBooks
            )
        )
    }

    private fun mockDatasetFirstRecyclerView(
        dataset: ArrayList<NoTitleListBModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                RecommendedActivity.FIRST_RECYCLERVIEW_NB_COLUMNS,
                RecommendedActivity.NB_BOOKS_FIRST_RECYCLERVIEW,
                allBooks
            )
        )
    }

    private fun mockDatasetPopularGenre(dataset: ArrayList<GModel>) {
        dataset.addAll(GModelProvider(context, languageCode).getPopularGenres())
    }

    private fun mockDatasetSecondRecyclerView(
        dataset: ArrayList<NoTitleListBModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                RecommendedActivity.SECOND_RECYCLERVIEW_NB_COLUMNS,
                RecommendedActivity.NB_BOOKS_SECOND_RECYCLERVIEW,
                allBooks
            )
        )
    }

    private fun mockDatasetSmallRecyclerView(
        dataset: ArrayList<NoTitleListBModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                RecommendedActivity.SMALL_RECYCLERVIEW_NB_COLUMNS,
                RecommendedActivity.NB_BOOKS_SMALL_RECYCLERVIEW,
                allBooks
            )
        )
    }
}