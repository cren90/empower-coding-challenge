/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ratecard

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

const val INCREMENT_AMOUNT = 1 //1 cent
const val MAX_RATE = 999 //$9.99
const val MIN_RATE = 0 //$0.00

class RateCardModel @Inject constructor(private val repo: RateCardRepo) {

    val indexFlow = MutableStateFlow<Int>(1)
    fun setIndex(index: Int) {
        indexFlow.value = index
    }

    val ratesFlow = repo.getRates()

    fun decrementRate(rate: Int): Int {
        return Math.max(rate - INCREMENT_AMOUNT, MIN_RATE)
    }

    fun incrementRate(rate: Int): Int {
        return Math.min(rate + INCREMENT_AMOUNT, MAX_RATE)
    }

    fun shouldWarn(customRate: Int, suggestedRate: Int): Boolean {
        return (customRate.toDouble()/suggestedRate.toDouble() > 1.15)
    }

    fun updateValues(
        isUsingSuggestedRates: Boolean,
        customMinimumFare: Int,
        customBaseFare: Int,
        customPerMinuteFare: Int,
        customPerMileFare: Int
    ) {
        repo.updateRates(isUsingSuggestedRates,
                         customMinimumFare,
                         customBaseFare,
                         customPerMinuteFare,
                         customPerMileFare)
    }
}