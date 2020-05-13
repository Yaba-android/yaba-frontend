package com.github.nasrat_v.yaba_android.Listable.Author

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class AModel(
    var remoteId: String, var imagePath: String,
    var name: String, var desc: String,
    var booksId: ArrayList<String>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(remoteId)
        parcel.writeString(imagePath)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeStringList(booksId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AModel> {
        override fun createFromParcel(parcel: Parcel): AModel {
            return AModel(parcel)
        }

        override fun newArray(size: Int): Array<AModel?> {
            return arrayOfNulls(size)
        }
    }

}