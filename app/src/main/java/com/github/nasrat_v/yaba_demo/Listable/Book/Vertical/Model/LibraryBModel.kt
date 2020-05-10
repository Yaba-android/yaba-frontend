package com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.Model

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.DownloadListBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.GroupListBModel
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel

data class LibraryBModel(
    var downloadBooks: ArrayList<DownloadListBModel>,
    var groupBooks: ArrayList<GroupListBModel>,
    var allBooks: ArrayList<NoTitleListBModel>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        arrayListOf<DownloadListBModel>().apply {
            parcel.readList(this, DownloadListBModel::class.java.classLoader)
        },
        arrayListOf<GroupListBModel>().apply {
            parcel.readList(this, GroupListBModel::class.java.classLoader)
        },
        arrayListOf<NoTitleListBModel>().apply {
            parcel.readList(this, NoTitleListBModel::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(downloadBooks)
        parcel.writeList(groupBooks)
        parcel.writeList(allBooks)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LibraryBModel> {
        override fun createFromParcel(parcel: Parcel): LibraryBModel {
            return LibraryBModel(parcel)
        }

        override fun newArray(size: Int): Array<LibraryBModel?> {
            return arrayOfNulls(size)
        }
    }
}