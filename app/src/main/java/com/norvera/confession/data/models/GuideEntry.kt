package com.norvera.confession.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "guide_main")
@Parcelize
data class GuideEntry(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id: Long,
    @ColumnInfo(name = "h_id") val headerId: Int,
    @ColumnInfo(name = "g_id") val guideId: Int,
    @ColumnInfo(name = "g_title") val guideTitle: String,
    @ColumnInfo(name = "text") val guideText: String,
    @ColumnInfo(name = "img_name") val imgName: String
) : Parcelable