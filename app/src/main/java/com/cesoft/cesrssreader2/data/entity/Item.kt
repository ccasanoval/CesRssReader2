package com.cesoft.cesrssreader2.data.entity

import android.os.Parcel
import android.os.Parcelable

data class Item(
    val id: String?,
    val guid: String,
    val title: String,
    val author: String,
    val link: String,
    val pubDate: String,
    val created: Long,
    val description: String,
    var content: String,//TODO: check again the relationship btw description and content!!
    var image: String,
    var categories: String
)
    : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.apply {
            writeString(id)
            writeString(guid)
            writeString(title)
            writeString(author)
            writeString(link)
            writeString(pubDate)
            writeLong(created)
            writeString(description)
            writeString(content)
            writeString(image)
            writeString(categories)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val TAG: String = Item::class.simpleName!!
        val CREATOR = object : Parcelable.Creator<Item> {
            override fun createFromParcel(parcel: Parcel): Item {
                return Item(parcel)
            }

            override fun newArray(size: Int): Array<Item?> {
                return arrayOfNulls(size)
            }
        }
    }
}
