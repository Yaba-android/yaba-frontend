package com.github.nasrat_v.maktaba_demo.AsyncTask

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.github.nasrat_v.maktaba_demo.Activity.GroupActivity
import com.github.nasrat_v.maktaba_demo.Listable.Book.Horizontal.Model.GroupBModel
import com.github.nasrat_v.maktaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel

class GroupNoTitleListBModelAsynFetchData(
    context: Context,
    private var selectedGroup: GroupBModel
) :
    androidx.loader.content.AsyncTaskLoader<ArrayList<NoTitleListBModel>>(context) {

    override fun loadInBackground(): ArrayList<NoTitleListBModel>? {
        //android.os.Debug.waitForDebugger()

        // on format en deux listes (vertical & horizontal) pour recylerviews
        return getGroupBooksFormatedForAdapter()
    }

    private fun getGroupBooksFormatedForAdapter(): ArrayList<NoTitleListBModel> {
        var noTitle: NoTitleListBModel
        var slice: Int
        val listNoTitleList = arrayListOf<NoTitleListBModel>()
        val sizeColumns = getNbColumns(GroupActivity.GROUP_NB_BOOK_PER_ROW, selectedGroup.bookModels.size)
        val offset = if (selectedGroup.bookModels.size < GroupActivity.GROUP_NB_BOOK_PER_ROW)
            selectedGroup.bookModels.size
        else
            GroupActivity.GROUP_NB_BOOK_PER_ROW

        for (index in 0..(sizeColumns - 1)) {
            slice = (index * offset)
            noTitle = addBooksToRows(slice, (slice + offset), getNbBooksAdded(listNoTitleList))
            listNoTitleList.add(noTitle)
        }
        return listNoTitleList
    }

    private fun getNbBooksAdded(list: ArrayList<NoTitleListBModel>): Int {
        var nb = 0

        list.forEach {
            nb += it.bookModels.size
        }
        return nb
    }

    private fun addBooksToRows(firstSlice: Int, lastSlice: Int, nbBooksAdded: Int): NoTitleListBModel {
        return if (((selectedGroup.bookModels.size % 2) != 0)
            && (nbBooksAdded == (selectedGroup.bookModels.size - 1))
        ) { // si la taille est impair et que c'est le dernier item -> juste le dernier
            NoTitleListBModel(arrayListOf(selectedGroup.bookModels.last()))
        } else {
            NoTitleListBModel(
                ArrayList( // sinon de 2 en 2
                    selectedGroup.bookModels.subList(firstSlice, lastSlice)
                )
            )
        }
    }

    private fun getNbColumns(nbRows: Int, nbBooks: Int): Int {
        if ((nbBooks % 2) == 0) {
            return (nbBooks / nbRows)
        }
        return ((nbBooks / nbRows) + 1)
    }
}