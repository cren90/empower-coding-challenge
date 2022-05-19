/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ratecard

import com.cren90.empower.empowerchallenge.ratecard.dto.RateCards
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateCardRepo @Inject constructor(private val retrofit: Retrofit) {

    val api = retrofit.create<RateCardAPI>()

    private val rateCards: MutableStateFlow<RateCards?> = MutableStateFlow(null)

    fun getRates(): Flow<RateCards> {
        if(rateCards.value == null) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.getRates()
                when(response) {
                    is NetworkResponse.Success      -> {
                        rateCards.value = response.body
                    }
                    is NetworkResponse.ServerError  -> {
                        //Error handling here
                    }
                    is NetworkResponse.NetworkError -> {
                        //Error handling here
                    }
                    is NetworkResponse.UnknownError -> {
                        //Error handling here
                    }
                }
            }
        }

        return rateCards.filterNotNull()
    }

    fun updateRates(
        usingSuggestedRates: Boolean,
        customMinimumFare: Int,
        customBaseFare: Int,
        customPerMinuteFare: Int,
        customPerMileFare: Int
    ) {

    }
}