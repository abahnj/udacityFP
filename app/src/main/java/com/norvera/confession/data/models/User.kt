package com.norvera.confession.data.models

import android.os.Parcelable
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.android.parcel.Parcelize

class User(vocation: Int, age: Int, gender: Int)  {
    private var vocation: String? = null
    private var gender: String? = null
    private var age: String? = null

    init {

        when (vocation) {
            0 -> this.vocation = " SINGLE = 1"
            1 -> this.vocation = " MARRIED = 1"
            2 -> this.vocation = " PRIEST = 1"
            3 -> this.vocation = " RELIGIOUS = 1"
        }
        when (age) {
            0 -> this.age = " ADULT = 1"
            1 -> this.age = " TEEN = 1"
            2 -> this.age = " CHILD = 1"
        }
        when (gender) {
            0 -> this.gender = " MALE = 1"
            1 -> this.gender = " FEMALE = 1"
        }
    }

    fun getUserSelectionForCommandment(commandment: Long): SimpleSQLiteQuery {
        val and = " AND"
        val selection =
            "SELECT * FROM SIN WHERE COMMANDMENT_ID = $commandment$and$vocation$and$age$and$gender"
        return SimpleSQLiteQuery(selection)
    }

}
