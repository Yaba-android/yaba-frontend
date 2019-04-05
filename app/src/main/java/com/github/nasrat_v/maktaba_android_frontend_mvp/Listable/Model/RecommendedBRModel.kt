package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Model

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.BModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.ListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel

data class RecommendedBRModel(
    var booksCarousel: ArrayList<BModel>,
    var booksFirstRecyclerView: ArrayList<NoTitleListBModel>,
    var popularGenre: ArrayList<GModel>,
    var booksSecondRecyclerView: ArrayList<NoTitleListBModel>,
    var booksSmallRecyclerView: ArrayList<NoTitleListBModel>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        arrayListOf<BModel>().apply {
            parcel.readList(this, BModel::class.java.classLoader)
        },
        arrayListOf<NoTitleListBModel>().apply {
            parcel.readList(this, NoTitleListBModel::class.java.classLoader)
        },
        arrayListOf<GModel>().apply {
            parcel.readList(this, GModel::class.java.classLoader)
        },
        arrayListOf<NoTitleListBModel>().apply {
            parcel.readList(this, NoTitleListBModel::class.java.classLoader)
        },
        arrayListOf<NoTitleListBModel>().apply {
            parcel.readList(this, NoTitleListBModel::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(booksCarousel)
        parcel.writeList(booksFirstRecyclerView)
        parcel.writeList(popularGenre)
        parcel.writeList(booksSecondRecyclerView)
        parcel.writeList(booksSmallRecyclerView)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecommendedBRModel> {
        override fun createFromParcel(parcel: Parcel): RecommendedBRModel {
            return RecommendedBRModel(parcel)
        }

        override fun newArray(size: Int): Array<RecommendedBRModel?> {
            return arrayOfNulls(size)
        }
    }
}