/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ui.ratecard.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RateCards(
    @Json(name="standard")
    val standardRateCard: RateCard,

    @Json(name="premium")
    val premiumRateCard: RateCard
)