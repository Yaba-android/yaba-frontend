package com.github.nasrat_v.yaba_demo.Listable.Book.Horizontal.Model

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.yaba_demo.Listable.Author.AModel
import com.github.nasrat_v.yaba_demo.Listable.Genre.GModel

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class BModel(
    var remoteId: String, var imagePath: String,
    var title: String, var author: AModel,
    var rating: Float, var numberRating: Int, var price: Float,
    var length: Int, var genre: GModel, var fileSize: String,
    var country: String, var datePublication: String,
    var publisher: String, var resume: String, var filePath: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<AModel>(AModel::class.java.classLoader),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readParcelable<GModel>(GModel::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(remoteId)
        parcel.writeString(imagePath)
        parcel.writeString(title)
        parcel.writeParcelable(author, flags)
        parcel.writeFloat(rating)
        parcel.writeInt(numberRating)
        parcel.writeFloat(price)
        parcel.writeInt(length)
        parcel.writeParcelable(genre, flags)
        parcel.writeString(fileSize)
        parcel.writeString(country)
        parcel.writeString(datePublication)
        parcel.writeString(publisher)
        parcel.writeString(resume)
        parcel.writeString(filePath)
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