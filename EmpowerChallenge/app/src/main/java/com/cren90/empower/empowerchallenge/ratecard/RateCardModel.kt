/**
 * Created by Chris Renfrow on 8/1/21.
 */

package com.cren90.empower.empowerchallenge.ratecard

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RateCardModel @Inject constructor(private val repo: RateCardRepo) {

    val indexFlow = MutableStateFlow<Int>(1)
    fun setIndex(index: Int) {
        indexFlow.value = index
    }

    val ratesFlow = repo.getRates()
}