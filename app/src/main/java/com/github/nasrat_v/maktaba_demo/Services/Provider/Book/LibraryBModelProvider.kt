package com.github.nasrat_v.maktaba_demo.Services.Provider.Book

import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_demo.Listable.Genre.GModel

class LibraryBModelProvider {

    fun getGroupListFromList(nb: Int, allBooks: ArrayList<NoTitleListBModel>)
            : ArrayList<GroupListBModel> {

        val genresSelected = getRandomGenresFromList(allBooks)
        val booksSelected = arrayListOf<GroupListBModel>()

        for (index in 0..(getNbColumns(nb, genresSelected.size) - 1)) {
            booksSelected.add(
                GroupListBModel(
                    getGroupFromList(nb, allBooks, genresSelected)
                )
            )
        }
        return booksSelected
    }

    private fun getNbColumns(nbRows: Int, nbGenres: Int): Int {
        if ((nbGenres % 2) == 0) {
            return (nbGenres / nbRows)
        }
        return ((nbGenres / nbRows) + 1)
    }

    private fun getGroupFromList(
        nb: Int, allBooks: ArrayList<NoTitleListBModel>,
        genresSelected: ArrayList<GModel>
    ): ArrayList<GroupBModel> {

        val booksSelected = arrayListOf<GroupBModel>()

        for (index in 0..(nb - 1)) {
            findBookFromGenre(genresSelected, allBooks, booksSelected)
        }
        return booksSelected
    }

    private fun findBookFromGenre(
        genresSelected: ArrayList<GModel>,
        allBooks: ArrayList<NoTitleListBModel>,
        booksSelected: ArrayList<GroupBModel>
    ) {

        allBooks.forEach {
            if (genresSelected.size == 0)
                return
            addSelectedBook(genresSelected.first(), it.bookModels, booksSelected)
        }
        genresSelected.remove(genresSelected.first())
    }


    private fun addSelectedBook(
        genre: GModel, list: ArrayList<BModel>,
        booksSelected: ArrayList<GroupBModel>
    ) {
        val filteredList = ArrayList(list.filter { it.genre == genre })

        if (filteredList.isNotEmpty()) {
            if (!booksSelected.isEmpty() && (booksSelected.last().genre == genre))
                booksSelected.last().bookModels.addAll(filteredList)
            else
                booksSelected.add(GroupBModel(genre, filteredList))
        }
    }

    private fun getRandomGenresFromList(list: ArrayList<NoTitleListBModel>): ArrayList<GModel> {
        val genresSelected = arrayListOf<GModel>()

        list.forEach {
            addSelectedGenre(it.bookModels, genresSelected)
        }
        return genresSelected
    }

    private fun addSelectedGenre(list: ArrayList<BModel>, genresSelected: ArrayList<GModel>) {
        list.forEach { bmodel ->
            if (genresSelected.find { it == bmodel.genre } == null)
                genresSelected.add(bmodel.genre)
        }
    }
}