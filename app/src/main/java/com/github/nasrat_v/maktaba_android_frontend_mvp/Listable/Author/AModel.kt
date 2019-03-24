package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Author

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class AModel(var picture: Int, var name: String, var desc: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(picture)
        parcel.writeString(name)
        parcel.writeString(desc)
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