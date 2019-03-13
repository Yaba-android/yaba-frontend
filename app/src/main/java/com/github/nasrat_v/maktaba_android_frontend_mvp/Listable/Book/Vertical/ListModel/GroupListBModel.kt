package com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Vertical.ListModel

import android.os.Parcel
import android.os.Parcelable
import com.github.nasrat_v.maktaba_android_frontend_mvp.Listable.Book.Horizontal.Model.GroupBModel

data class GroupListBModel(var groupModels: ArrayList<GroupBModel>) : Parcelable {

    constructor(parcel: Parcel) : this(
        arrayListOf<GroupBModel>().apply {
            parcel.readList(this, GroupBModel::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(groupModels)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupListBModel> {
        override fun createFromParcel(parcel: Parcel): GroupListBModel {
            return GroupListBModel(parcel)
        }

        override fun newArray(size: Int): Array<GroupListBModel?> {
            return arrayOfNulls(size)
        }
    }
}