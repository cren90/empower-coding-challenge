/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ui.ratecard

import com.cren90.empower.empowerchallenge.ui.ratecard.dto.RateCards
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface RateCardAPI {

    @GET("rate")
    suspend fun getRates(): NetworkResponse<RateCards, Unit>
}