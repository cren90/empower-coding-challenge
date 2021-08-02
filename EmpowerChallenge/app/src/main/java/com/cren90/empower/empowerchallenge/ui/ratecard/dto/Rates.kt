/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ui.ratecard.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rates(
    @Json(name="min")
    val minimum: Int,

    @Json(name="base")
    val base: Int,

    @Json(name="perMin")
    val perMinute: Int,

    @Json(name="perMile")
    val perMile: Int,

)