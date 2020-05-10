package com.github.nasrat_v.yaba_demo.Listable.Model

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.yaba_demo.Listable.Book.Vertical.ListModel.NoTitleListBModel
import com.github.nasrat_v.yaba_demo.Listable.Review.Vertical.RModel

data class BookDetailsBRModel(
    var books: ArrayList<NoTitleListBModel>,
    var reviews: ArrayList<RModel>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        arrayListOf<NoTitleListBModel>().apply {
            parcel.readList(this, NoTitleListBModel::class.java.classLoader)
        },
        arrayListOf<RModel>().apply {
            parcel.readList(this, RModel::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(books)
        parcel.writeList(reviews)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookDetailsBRModel> {
        override fun createFromParcel(parcel: Parcel): BookDetailsBRModel {
            return BookDetailsBRModel(parcel)
        }

        override fun newArray(size: Int): Array<BookDetailsBRModel?> {
            return arrayOfNulls(size)
        }
    }
}