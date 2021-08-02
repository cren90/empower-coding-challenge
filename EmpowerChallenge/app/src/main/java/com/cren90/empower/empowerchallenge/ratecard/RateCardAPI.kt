/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ratecard

import com.cren90.empower.empowerchallenge.ratecard.dto.RateCards
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

interface RateCardAPI {

    @GET("rate")
    suspend fun getRates(): NetworkResponse<RateCards, Unit>
}