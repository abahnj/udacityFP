package com.norvera.confession.data.models

import android.os.Parcel
import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "SIN")
class ExaminationEntry : Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Long

    @ColumnInfo(name = "COMMANDMENT_ID")
    val commandmentId: Int

    @ColumnInfo(name = "ADULT")
    val adult: Int

    @ColumnInfo(name = "SINGLE")
    val single: Int

    @ColumnInfo(name = "MARRIED")
    val married: Int

    @ColumnInfo(name = "RELIGIOUS")
    val religious: Int

    @ColumnInfo(name = "PRIEST")
    val priest: Int

    @ColumnInfo(name = "TEEN")
    val teen: Int

    @ColumnInfo(name = "FEMALE")
    val female: Int

    @ColumnInfo(name = "MALE")
    val male: Int

    @ColumnInfo(name = "CHILD")
    val child: Int

    @ColumnInfo(name = "CUSTOM_ID")
    val customId: Int

    @ColumnInfo(name = "DESCRIPTION")
    var description: String

    @ColumnInfo(name = "COUNT")
    var count: Int = 0

    constructor(
        id: Long,
        commandmentId: Int,
        adult: Int,
        single: Int,
        married: Int,
        religious: Int,
        priest: Int,
        teen: Int,
        female: Int,
        male: Int,
        child: Int,
        customId: Int,
        description: String, count: Int
    ) {
        this.id = id
        this.commandmentId = commandmentId
        this.adult = adult
        this.single = single
        this.married = married
        this.religious = religious
        this.priest = priest
        this.teen = teen
        this.female = female
        this.male = male
        this.child = child
        this.customId = customId
        this.description = description
        this.count = count
    }


    @Ignore
    protected constructor(`in`: Parcel) {
        id = `in`.readLong()
        commandmentId = `in`.readInt()
        adult = `in`.readInt()
        single = `in`.readInt()
        married = `in`.readInt()
        religious = `in`.readInt()
        priest = `in`.readInt()
        teen = `in`.readInt()
        female = `in`.readInt()
        male = `in`.readInt()
        child = `in`.readInt()
        customId = `in`.readInt()
        description = `in`.readString()
        count = `in`.readInt()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeInt(commandmentId)
        dest.writeInt(adult)
        dest.writeInt(single)
        dest.writeInt(married)
        dest.writeInt(religious)
        dest.writeInt(priest)
        dest.writeInt(teen)
        dest.writeInt(female)
        dest.writeInt(male)
        dest.writeInt(child)
        dest.writeInt(customId)
        dest.writeString(description)
        dest.writeInt(count)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as ExaminationEntry?

        if (id != that!!.id) return false
        if (commandmentId != that.commandmentId) return false
        if (adult != that.adult) return false
        if (single != that.single) return false
        if (married != that.married) return false
        if (religious != that.religious) return false
        if (priest != that.priest) return false
        if (teen != that.teen) return false
        if (female != that.female) return false
        if (male != that.male) return false
        if (child != that.child) return false
        if (customId != that.customId) return false
        return if (count != that.count) false else description == that.description
    }

    override fun hashCode(): Int {
        var result = (id xor id.ushr(32)).toInt()
        result = 31 * result + commandmentId
        result = 31 * result + adult
        result = 31 * result + single
        result = 31 * result + married
        result = 31 * result + religious
        result = 31 * result + priest
        result = 31 * result + teen
        result = 31 * result + female
        result = 31 * result + male
        result = 31 * result + child
        result = 31 * result + customId
        result = 31 * result + description.hashCode()
        result = 31 * result + count
        return result
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<ExaminationEntry> =
            object : Parcelable.Creator<ExaminationEntry> {
                override fun createFromParcel(`in`: Parcel): ExaminationEntry {
                    return ExaminationEntry(`in`)
                }

                override fun newArray(size: Int): Array<ExaminationEntry?> {
                    return arrayOfNulls(size)
                }
            }
    }
}
