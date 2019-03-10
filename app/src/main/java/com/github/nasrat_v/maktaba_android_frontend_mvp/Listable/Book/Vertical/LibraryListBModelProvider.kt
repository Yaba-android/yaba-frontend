package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical

import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel

class LibraryListBModelProvider {

    fun getGroupListFromList(allBooks: ArrayList<NoTitleListBModel>)
            : ArrayList<GroupListBModel> {

        val genresSelected = getRandomGenresFromList(allBooks)
        val booksSelected = arrayListOf<GroupListBModel>()

        genresSelected.forEach { genre ->
            booksSelected.add(
                GroupListBModel(
                    getGroupFromList(allBooks, genre)
                )
            )
        }
        return booksSelected
    }

    private fun getGroupFromList(allBooks: ArrayList<NoTitleListBModel>, genre: GModel)
            : ArrayList<GroupBModel> {

        val booksSelected = arrayListOf<GroupBModel>()

        allBooks.forEach {
            addSelectedBook(genre, it.bookModels, booksSelected)
        }
        return booksSelected
    }

    private fun addSelectedBook(genre: GModel, list: ArrayList<BModel>, booksSelected: ArrayList<GroupBModel>) {
        val filteredList = ArrayList<BModel>(
            list.filter {
                it.genre == genre
            }
        )
        if (filteredList.size > 0)
            booksSelected.add(GroupBModel(genre, filteredList))
    }

    private fun getRandomGenresFromList(list: ArrayList<NoTitleListBModel>) : ArrayList<GModel> {
        val genresSelected = arrayListOf<GModel>()

        list.forEach {
            addSelectedGenre(it.bookModels, genresSelected)
        }
        return genresSelected
    }

    private fun addSelectedGenre(list: ArrayList<BModel>, genresSelected: ArrayList<GModel>) {
        list.forEach {
            if (!genresSelected.contains(it.genre)) {
                genresSelected.add(it.genre)
            }
        }
    }
}