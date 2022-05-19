/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ratecard

import com.cren90.empower.empowerchallenge.ratecard.dto.RateCards
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RateCardAPI {

    @GET("rate")
    suspend fun getRates(): NetworkResponse<RateCards, Unit>

    @PUT("rate/{rate_card_id}")
    suspend fun putRates(
        @Path("rate_card_id") rateCardID: Int,
        @Body rates: RateCards
    ): NetworkResponse<Unit, Unit>
}