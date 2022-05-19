/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ratecard.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RateCard(
    @Json(name="useSuggested")
    val useSuggestedRates: Boolean,

    @Json(name="custom")
    val customRates: Rates,

    @Json(name="suggested")
    val suggestedRates: Rates
)