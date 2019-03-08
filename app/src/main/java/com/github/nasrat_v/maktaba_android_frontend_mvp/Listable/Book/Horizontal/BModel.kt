package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Genre.GModel

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BModel(var image: Int, var title: String, var author: String,
             var rating: Float, var numberRating: Int, var price: Float,
             var length: Int, var genre: GModel, var fileSize: String,
             var country: String, var datePublication: String, var publisher: String)
    : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readParcelable(GModel::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeFloat(rating)
        parcel.writeInt(numberRating)
        parcel.writeFloat(price)
        parcel.writeInt(length)
        parcel.writeParcelable(genre, flags)
        parcel.writeString(fileSize)
        parcel.writeString(country)
        parcel.writeString(datePublication)
        parcel.writeString(publisher)
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