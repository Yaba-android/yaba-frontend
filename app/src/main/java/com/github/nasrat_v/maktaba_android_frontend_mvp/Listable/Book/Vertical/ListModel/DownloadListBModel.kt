package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.DownloadBModel

data class DownloadListBModel(var bookModels: ArrayList<DownloadBModel>)
    : Parcelable {

    constructor(parcel: Parcel) : this(
        arrayListOf<DownloadBModel>().apply {
            parcel.readList(this, DownloadBModel::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(bookModels)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DownloadListBModel> {
        override fun createFromParcel(parcel: Parcel): DownloadListBModel {
            return DownloadListBModel(parcel)
        }

        override fun newArray(size: Int): Array<DownloadListBModel?> {
            return arrayOfNulls(size)
        }
    }
}