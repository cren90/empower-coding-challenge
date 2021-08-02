package com.cren90.empower.empowerchallenge.ui.ratecard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RateCardViewModel @Inject constructor(private val rateCardModel: RateCardModel) : ViewModel() {



    fun setIndex(index: Int) {
        rateCardModel.setIndex(index)
    }

    val suggestedMinimumFare = MutableLiveData<String>("")
    val customMinimumFare = MutableLiveData<String>("")
    val suggestedBaseFare = MutableLiveData<String>("")
    val customBaseFare = MutableLiveData<String>("")
    val suggestedPerMinuteFare = MutableLiveData<String>("")
    val customPerMinuteFare = MutableLiveData<String>("")
    val suggestedPerMileFare = MutableLiveData<String>("")
    val customPerMileFare = MutableLiveData<String>("")
    val isUsingSuggestedRates = MutableLiveData<Boolean>()

    init {

        viewModelScope.launch {
            rateCardModel.ratesFlow.combine(rateCardModel.indexFlow) { rates, index ->
                if (index == 1) {
                    rates.standardRateCard
                } else {
                    rates.premiumRateCard
                }
            }.collect { rateCard ->
                val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
                suggestedMinimumFare.value = numberFormat.format( BigDecimal(rateCard.suggestedRates.minimum).movePointLeft(2))
                customMinimumFare.value = numberFormat.format( BigDecimal(rateCard.customRates.minimum).movePointLeft(2))
                suggestedBaseFare.value = numberFormat.format( BigDecimal(rateCard.suggestedRates.base).movePointLeft(2))
                customBaseFare.value = numberFormat.format( BigDecimal(rateCard.customRates.base).movePointLeft(2))
                suggestedPerMinuteFare.value = numberFormat.format( BigDecimal(rateCard.suggestedRates.perMinute).movePointLeft(2))
                customPerMinuteFare.value = numberFormat.format( BigDecimal(rateCard.customRates.perMinute).movePointLeft(2))
                suggestedPerMileFare.value = numberFormat.format( BigDecimal(rateCard.suggestedRates.perMile).movePointLeft(2))
                customPerMileFare.value = numberFormat.format( BigDecimal(rateCard.customRates.perMile).movePointLeft(2))
                isUsingSuggestedRates.value = rateCard.useSuggestedRates
            }
        }
    }

}