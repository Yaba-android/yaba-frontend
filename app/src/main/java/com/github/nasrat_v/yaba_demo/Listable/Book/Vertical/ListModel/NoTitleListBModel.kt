package com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model.BModel

data class NoTitleListBModel(var bookModels: ArrayList<BModel>) : Parcelable {

    constructor(parcel: Parcel) : this(
        arrayListOf<BModel>().apply {
            parcel.readList(this, BModel::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(bookModels)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoTitleListBModel> {
        override fun createFromParcel(parcel: Parcel): NoTitleListBModel {
            return NoTitleListBModel(parcel)
        }

        override fun newArray(size: Int): Array<NoTitleListBModel?> {
            return arrayOfNulls(size)
        }
    }
}