package com.norvera.confession.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "COMMANDMENTS")
@Parcelize
data class CommandmentEntry
// column names commandment table's columns

    (
    @field:PrimaryKey
    @field:ColumnInfo(name = "_id")
    val id: Long,
    @field:ColumnInfo(name = "NUMBER")
    val number: Int,
    @field:ColumnInfo(name = "TEXT")
    val text: String?,
    @field:ColumnInfo(name = "CATEGORY")
    val category: String,
    @field:ColumnInfo(name = "COMMANDMENT")
    val commandment: String,
    @field:ColumnInfo(name = "CUSTOM_ID")
    val customId: Int?
) : Parcelable
