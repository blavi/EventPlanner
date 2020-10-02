package com.example.pimpmywed.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Guests")
data class GuestsEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "invited_number") var invited_number: String,
    @ColumnInfo(name = "confirmed_number") var confirmed_number: String,
    @ColumnInfo(name = "invited") var invited: String,
    @ColumnInfo(name = "menu") var menu: String,
    @ColumnInfo(name = "age") var age: String,
    @ColumnInfo(name = "table") var table: String,
    @ColumnInfo(name = "checked") var checked: String,
    @ColumnInfo(name = "identifier") var identifier: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    constructor() : this(0, "", "", "", "", "", "", "", "", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(invited_number)
        parcel.writeString(confirmed_number)
        parcel.writeString(invited)
        parcel.writeString(menu)
        parcel.writeString(age)
        parcel.writeString(table)
        parcel.writeString(checked)
        parcel.writeString(identifier)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GuestsEntity> {
        override fun createFromParcel(parcel: Parcel): GuestsEntity {
            return GuestsEntity(parcel)
        }

        override fun newArray(size: Int): Array<GuestsEntity?> {
            return arrayOfNulls(size)
        }
    }

    fun isChecked(): Boolean {
        return checked == "1"
    }
}