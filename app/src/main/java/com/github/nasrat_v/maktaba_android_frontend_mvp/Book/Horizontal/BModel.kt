package com.github.nasrat_v.maktaba_android_frontend_mvp.Book.Horizontal

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BModel(var image: Int, var title: String, var author: String,
             var rating: Float, var numberRating: Int, var price: Float)
    : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeFloat(rating)
        parcel.writeInt(numberRating)
        parcel.writeFloat(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BModel> {
        override fun createFromParcel(parcel: Parcel): BModel {
            return BModel(parcel)
        }

        override fun newArray(size: Int): Array<BModel?> {
            return arrayOfNulls(size)
        }
    }
}