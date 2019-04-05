package com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.RecommendedActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Model.BookDetailsBRModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Model.RecommendedBRModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Review.Vertical.RModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.R
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelRandomProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Review.RModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.TabFragment.BookDetailsContainerFragment

class RecommendedBRModelAsyncFetchData(
    context: Context
) :
    AsyncTaskLoader<RecommendedBRModel>(context) {

    override fun loadInBackground(): RecommendedBRModel? {
        //android.os.Debug.waitForDebugger()

        val allBooksFromDatabase = fetchAllBooksFromDatabase()
        val datasetCarousel = arrayListOf<BModel>()
        val datasetFirst = arrayListOf<NoTitleListBModel>()
        val datasetPopularGenre = arrayListOf<GModel>()
        val datasetSecond = arrayListOf<NoTitleListBModel>()
        val datasetSmall = arrayListOf<NoTitleListBModel>()

        mockDatasetCarousel(datasetCarousel)
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
        return BModelProvider(context).getAllBooksFromDatabase()
    }

    private fun mockDatasetCarousel(dataset: ArrayList<BModel>) {
        dataset.addAll(
            BModelRandomProvider(context).getRandomsInstances(
                RecommendedActivity.NB_BOOKS_CAROUSEL
            )
        )
    }

    private fun mockDatasetFirstRecyclerView(
        dataset: ArrayList<NoTitleListBModel>,
        allBooksFromDatabase: ArrayList<BModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context).getRandomsInstancesFromListToNoTitleListBModel(
                RecommendedActivity.FIRST_RECYCLERVIEW_NB_COLUMNS,
                RecommendedActivity.NB_BOOKS_FIRST_RECYCLERVIEW,
                allBooksFromDatabase
            )
        )
    }

    private fun mockDatasetPopularGenre(dataset: ArrayList<GModel>) {
        dataset.addAll(GModelProvider(context).getPopularGenres())
    }

    private fun mockDatasetSecondRecyclerView(
        dataset: ArrayList<NoTitleListBModel>,
        allBooksFromDatabase: ArrayList<BModel>
    ) {
        dataset.addAll(
            BModelRandomProvider(context).getRandomsInstancesFromListToNoTitleListBModel(
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
            BModelRandomProvider(context).getRandomsInstancesFromListToNoTitleListBModel(
                RecommendedActivity.SMALL_RECYCLERVIEW_NB_COLUMNS,
                RecommendedActivity.NB_BOOKS_SMALL_RECYCLERVIEW,
                allBooksFromDatabase
            )
        )
    }
}