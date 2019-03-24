package com.github.nasrat_v.maktaba_android_frontend_mvp.AsyncTask

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.GroupActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.Activity.SectionActivity
import com.github.nasrat_v.maktaba_android_frontend_mvp.ICallback.IInputBrowseCallback
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Model.BrowseBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Book.BModelProvider
import com.github.nasrat_v.maktaba_android_frontend_mvp.Services.Provider.Genre.GModelProvider

class BrowseBModelAsynFetchData(
    context: Context,
    private var inputCallback: IInputBrowseCallback
) :
    AsyncTaskLoader<BrowseBModel>(context) {

    override fun loadInBackground(): BrowseBModel? {
        //android.os.Debug.waitForDebugger()

        val allBooksFromDatabase = fetchAllBooksFromDatabase()
        val booksResult = arrayListOf<BModel>()
        val booksByGenreResult = arrayListOf<ListBModel>()

        findBooks(booksResult, allBooksFromDatabase)
        if (booksResult.isNotEmpty()) {
            findBooksByGenre(booksByGenreResult, booksResult.first().genre)
        }
        return BrowseBModel(booksResult, booksByGenreResult)
    }

    private fun fetchAllBooksFromDatabase(): ArrayList<BModel> {
        return BModelProvider(context).getAllBooksFromDatabase()
    }

    private fun findBooks(result: ArrayList<BModel>, allBooksFromDatabase: ArrayList<BModel>) {
        val inputString = inputCallback.getInputBrowseString()

        result.addAll(
            allBooksFromDatabase.filter {
                isSearchMatching(it, inputString)
            }
        )
    }

    private fun findBooksByGenre(result: ArrayList<ListBModel>, genre: GModel) {
        val list = GModelProvider(context).getAllBooksFromGenre(genre)

        result.add(ListBModel(("Category: " + genre.name), list))
    }

    private fun isSearchMatching(book: BModel, str: String): Boolean {
        return (book.title.toLowerCase().contains(str) ||
                book.author.name.toLowerCase().contains(str) ||
                book.country.toLowerCase().contains(str) ||
                book.genre.name.toLowerCase().contains(str) ||
                book.publisher.toLowerCase().contains(str))
    }
}