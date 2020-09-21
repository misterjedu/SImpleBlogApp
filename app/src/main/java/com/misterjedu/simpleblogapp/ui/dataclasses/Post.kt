package com.misterjedu.simpleblogapp.ui.dataclasses

import android.os.Parcel
import android.os.Parcelable

data class Post(
    val userName: String,
    val postedDate: String,
    val tag: String,
    val readingTime: String,
    val imageId: String,
    val position: Int,
    val postTitle: String,
    val postBody: String,
    val postId: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(postedDate)
        parcel.writeString(tag)
        parcel.writeString(readingTime)
        parcel.writeString(imageId)
        parcel.writeInt(position)
        parcel.writeString(postTitle)
        parcel.writeString(postBody)
        parcel.writeString(postId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}