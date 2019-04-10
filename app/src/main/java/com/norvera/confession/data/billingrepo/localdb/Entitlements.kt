/**
 * Copyright (C) 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.norvera.confession.data.billingrepo.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val FULL_TANK = 4
private const val EMPTY_TANK = 0
const val GAS_PURCHASE = 1

abstract class Entitlement {
    @PrimaryKey
    var id: Int = 1
    /**
     * This method tells clients whether a user __should__ buy a particular item at the moment. For
     * example, if the gas tank is full the user should not be buying gas. This method is __not__
     * a reflection on whether Google Play Billing can make a purchase.
     */
    abstract fun mayPurchase(): Boolean
}

@Entity(tableName = "premium")
data class PremiumCar(val entitled: Boolean) : Entitlement() {
    override fun mayPurchase(): Boolean = !entitled
}

@Entity(tableName = "remove_ads")
data class GoldStatus(val entitled: Boolean) : Entitlement() {
    override fun mayPurchase(): Boolean = !entitled
}

@Entity(tableName = "gas_tank")
class GasTank(private var level: Int) : Entitlement() {

    fun getLevel() = level

    override fun mayPurchase(): Boolean = level < FULL_TANK

    fun needGas(): Boolean = level <= EMPTY_TANK

    fun decrement(by: Int = 1) {
        level -= by
    }

}