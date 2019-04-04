package com.norvera.confession.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "INSPIRATION")
@Parcelize
data class InspirationEntry(
    @field:PrimaryKey
    @field:ColumnInfo(name = "_id")
    val id: Long, @field:ColumnInfo(name = "QUOTE")
    val text: String, @field:ColumnInfo(name = "AUTHOR")
    val author: String
) : Parcelable
