package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class GroupBModel(var genre: GModel, var bookModels: ArrayList<BModel>)
    : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable<GModel>(GModel::class.java.classLoader),
        arrayListOf<BModel>().apply {
            parcel.readList(this, BModel::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(genre, flags)
        parcel.writeList(bookModels)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupBModel> {
        override fun createFromParcel(parcel: Parcel): GroupBModel {
            return GroupBModel(parcel)
        }

        override fun newArray(size: Int): Array<GroupBModel?> {
            return arrayOfNulls(size)
        }
    }
}