package com.github.nasrat_v.yaba_demo.Services.Provider.Book

import android.content.Context
import android.os.Environment
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.DownloadBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel
import java.io.*
import java.lang.Exception

class LibraryBModelProvider(private var context: Context, private var languageCode: String) {

    companion object {
        private const val FILEPATH_LIBRARY_ID = "yaba_library_books_id"
    }

    fun getAllBooksListBookFromList(
        nbX:Int,
        booksLibrary: ArrayList<BModel>
    ): ArrayList<NoTitleListBModel> {

        val allBooksList = arrayListOf<NoTitleListBModel>()
        val booksSelected = arrayListOf<BModel>()

        booksLibrary.forEach {

            if (booksSelected.size == nbX) {
                val tmpBooksSelected = arrayListOf<BModel>()

                tmpBooksSelected.addAll(booksSelected)
                allBooksList.add(NoTitleListBModel(tmpBooksSelected))
                booksSelected.clear()
            }
            booksSelected.add(it)
        }
        allBooksList.add(NoTitleListBModel(booksSelected))
        return allBooksList
    }

    fun getDownloadedListBookFromList(nbX: Int, allBooks: ArrayList<NoTitleListBModel>)
            : ArrayList<DownloadListBModel> {

        val simpleList = allBooksInSimpleList(allBooks)
        val booksSelected = arrayListOf<DownloadBModel>()
        val listBooksSelected = arrayListOf<DownloadListBModel>()
        val location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()

        simpleList.forEach {
            val file = File(location + '/' + it.filePath)

            if (file.exists()) {
                if (booksSelected.size == nbX) {
                    val tmpBooksSelected = arrayListOf<DownloadBModel>()

                    tmpBooksSelected.addAll(booksSelected)
                    listBooksSelected.add(DownloadListBModel(tmpBooksSelected))
                    booksSelected.clear()
                }
                booksSelected.add(DownloadBModel(it))
            }
        }
        listBooksSelected.add(DownloadListBModel(booksSelected))
        return listBooksSelected
    }

    fun getGroupListFromList(nb: Int, allBooks: ArrayList<NoTitleListBModel>)
            : ArrayList<GroupListBModel> {

        val genresSelected = getRandomGenresFromList(allBooks)
        val booksSelected = arrayListOf<GroupListBModel>()

        for (index in 0 until getNbColumns(nb, genresSelected.size)) {
            booksSelected.add(
                GroupListBModel(
                    getGroupFromList(nb, allBooks, genresSelected)
                )
            )
        }
        return booksSelected
    }

    fun fetchInternalStorageAllBooksLibraryId(): ArrayList<String?> {
        return try {
            var text: String? = null
            val fileInputStream: FileInputStream? = context.openFileInput(FILEPATH_LIBRARY_ID)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val bookIdList = arrayListOf<String?>()

            while ({ text = bufferedReader.readLine(); text }() != null) {
                bookIdList.add(text)
            }
            bookIdList
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    fun saveInternalStorageBookLibraryId(remoteId: String) {
        val fileOuputStream: FileOutputStream

        try {
            fileOuputStream = context.openFileOutput(FILEPATH_LIBRARY_ID, Context.MODE_PRIVATE)
            fileOuputStream.write(remoteId.toByteArray())
            fileOuputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

        for (index in 0 until nb) {
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
            if (booksSelected.isNotEmpty() && (booksSelected.last().genre == genre))
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

    private fun allBooksInSimpleList(allBooks: ArrayList<NoTitleListBModel>): ArrayList<BModel> {
        val simpleList = arrayListOf<BModel>()

        allBooks.forEach {
            it.bookModels.forEach { book ->
                simpleList.add(book)
            }
        }
        return simpleList
    }
}