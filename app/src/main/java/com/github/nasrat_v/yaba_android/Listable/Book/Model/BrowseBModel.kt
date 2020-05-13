package com.github.nasrat_v.yaba_android.Listable.Book.Model

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.yaba_android.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.yaba_android.Listable.Book.Vertical.ListModel.ListBModel

data class BrowseBModel(
    var booksResultList: ArrayList<BModel>,
    var booksGenreList: ArrayList<ListBModel>
) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        arrayListOf<BModel>().apply {
            parcel.readList(this, BModel::class.java.classLoader)
        },
        arrayListOf<ListBModel>().apply {
            parcel.readList(this, ListBModel::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(booksResultList)
        parcel.writeList(booksGenreList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BrowseBModel> {
        override fun createFromParcel(parcel: Parcel): BrowseBModel {
            return BrowseBModel(parcel)
        }

        override fun newArray(size: Int): Array<BrowseBModel?> {
            return arrayOfNulls(size)
        }
    }

}