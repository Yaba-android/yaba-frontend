package com.github.nasrat_v.maktaba_demo.AsyncTask

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_demo.Activity.RecommendedActivity
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_demo.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_demo.Listable.Model.RecommendedBRModel
import com.github.nasrat_v.maktaba_demo.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.maktaba_demo.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.maktaba_demo.Services.Provider.Genre.GModelProvider

class RecommendedBRModelAsyncFetchData(
    context: Context,
    private var languageCode: String
) :
    androidx.loader.content.AsyncTaskLoader<RecommendedBRModel>(context) {

    override fun loadInBackground(): RecommendedBRModel? {
        //android.os.Debug.waitForDebugger()

        val allBooksFromDatabase = fetchAllBooksFromDatabase()
        val datasetCarousel = arrayListOf<BModel>()
        val datasetFirst = arrayListOf<NoTitleListBModel>()
        val datasetPopularGenre = arrayListOf<GModel>()
        val datasetSecond = arrayListOf<NoTitleListBModel>()
        val datasetSmall = arrayListOf<NoTitleListBModel>()

        mockDatasetCarousel(datasetCarousel, allBooksFromDatabase)
        mockDatasetFirstRecyclerView(datasetFirst, allBooksFromDatabase)
        mockDatasetPopularGenre(datasetPopularGenre)
        mockDatasetSecondRecyclerView(datasetSecond, allBooksFromDatabase)
        mockDatasetSmallRecyclerView(datasetSmall, allBooksFromDatabase)

        return RecommendedBRModel(
            datasetCarousel, datasetFirst,
            datasetPopularGenre,
            datasetSecond, datasetSmall
        )
    }

    private fun fetchAllBooksFromDatabase(): ArrayList<BModel> {
        return BModelProvider(context, languageCode).getAllBooksFromDatabase()
    }

    private fun mockDatasetCarousel(
        dataset: ArrayList<BModel>,
        allBooksFromDatabase: ArrayList<BModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromList(
                RecommendedActivity.NB_BOOKS_CAROUSEL,
                allBooksFromDatabase
            )
        )
    }

    private fun mockDatasetFirstRecyclerView(
        dataset: ArrayList<NoTitleListBModel>,
        allBooksFromDatabase: ArrayList<BModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                RecommendedActivity.FIRST_RECYCLERVIEW_NB_COLUMNS,
                RecommendedActivity.NB_BOOKS_FIRST_RECYCLERVIEW,
                allBooksFromDatabase
            )
        )
    }

    private fun mockDatasetPopularGenre(dataset: ArrayList<GModel>) {
        dataset.addAll(GModelProvider(context, languageCode).getPopularGenres())
    }

    private fun mockDatasetSecondRecyclerView(
        dataset: ArrayList<NoTitleListBModel>,
        allBooksFromDatabase: ArrayList<BModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                RecommendedActivity.SECOND_RECYCLERVIEW_NB_COLUMNS,
                RecommendedActivity.NB_BOOKS_SECOND_RECYCLERVIEW,
                allBooksFromDatabase
            )
        )
    }

    private fun mockDatasetSmallRecyclerView(
        dataset: ArrayList<NoTitleListBModel>,
        allBooksFromDatabase: ArrayList<BModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context, languageCode).getRandomsInstancesFromListToNoTitleListBModel(
                RecommendedActivity.SMALL_RECYCLERVIEW_NB_COLUMNS,
                RecommendedActivity.NB_BOOKS_SMALL_RECYCLERVIEW,
                allBooksFromDatabase
            )
        )
    }
}