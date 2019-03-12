package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class DownloadBModel(var book: BModel) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable<BModel>(BModel::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(book, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DownloadBModel> {
        override fun createFromParcel(parcel: Parcel): DownloadBModel {
            return DownloadBModel(parcel)
        }

        override fun newArray(size: Int): Array<DownloadBModel?> {
            return arrayOfNulls(size)
        }
    }
}