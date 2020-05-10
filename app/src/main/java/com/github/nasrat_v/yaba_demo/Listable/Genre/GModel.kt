package com.github.nasrat_v.yaba_demo.Listable.Genre

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class GModel(var name: String, var nb: Int, var popular: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(nb)
        parcel.writeInt(popular)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GModel> {
        override fun createFromParcel(parcel: Parcel): GModel {
            return GModel(parcel)
        }

        override fun newArray(size: Int): Array<GModel?> {
            return arrayOfNulls(size)
        }
    }
}
