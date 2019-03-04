package com.github.nasrat_v.maktaba_android_frontend_mvp.Popular_species.Horizontal

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PSModel(var name: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PSModel> {
        override fun createFromParcel(parcel: Parcel): PSModel {
            return PSModel(parcel)
        }

        override fun newArray(size: Int): Array<PSModel?> {
            return arrayOfNulls(size)
        }
    }
}
