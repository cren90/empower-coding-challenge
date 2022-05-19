package com.cren90.empower.empowerchallenge.ratecard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cren90.empower.empowerchallenge.R
import com.cren90.empower.empowerchallenge.ratecard.RateCardModel
import com.cren90.livedataktx.extensions.dependentLiveData
import com.cren90.livedataktx.extensions.map
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

    private val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)

    fun setIndex(index: Int) {
        rateCardModel.setIndex(index)
    }

    val isSubmitEnabled = MutableLiveData<Boolean>(false)

    val isUsingSuggestedRates = MutableLiveData<Boolean>()
    val suggestedMinimumFare = MutableLiveData<String>()
    val suggestedMinimumFareStatus = MutableLiveData<RateStatus>(RateStatus.OK)

    val customMinimumFare = MutableLiveData<String>()
    val customMinimumFareStatus = MutableLiveData<RateStatus>(RateStatus.OK)
    val customMinimumFareColor = dependentLiveData(customMinimumFareStatus, isUsingSuggestedRates) {
        if(isUsingSuggestedRates.value == true) {
            R.color.trolley_grey
        } else if (customMinimumFareStatus.value == RateStatus.WARNING) {
            R.color.red_500
        } else {
            R.color.blue_500
        }
    }

    val suggestedBaseFare = MutableLiveData<String>()
    val suggestedBaseFareStatus = MutableLiveData<RateStatus>(RateStatus.OK)

    val customBaseFare = MutableLiveData<String>()
    val customBaseFareStatus = MutableLiveData<RateStatus>(RateStatus.OK)
    val customBaseFareColor = dependentLiveData(customBaseFareStatus, isUsingSuggestedRates) {
        if(isUsingSuggestedRates.value == true) {
            R.color.trolley_grey
        } else if (customBaseFareStatus.value == RateStatus.WARNING) {
            R.color.red_500
        } else {
            R.color.blue_500
        }
    }

    val suggestedPerMinuteFare = MutableLiveData<String>()
    val suggestedPerMinuteFareStatus = MutableLiveData<RateStatus>(RateStatus.OK)

    val customPerMinuteFare = MutableLiveData<String>()
    val customPerMinuteFareStatus = MutableLiveData<RateStatus>(RateStatus.OK)
    val customPerMinuteFareColor = dependentLiveData(customPerMinuteFareStatus, isUsingSuggestedRates) {
        if(isUsingSuggestedRates.value == true) {
            R.color.trolley_grey
        } else if (customPerMinuteFareStatus.value == RateStatus.WARNING) {
            R.color.red_500
        } else {
            R.color.blue_500
        }
    }

    val suggestedPerMileFare = MutableLiveData<String>()
    val suggestedPerMileFareStatus = MutableLiveData<RateStatus>(RateStatus.OK)

    val customPerMileFare = MutableLiveData<String>()
    val customPerMileFareStatus = MutableLiveData<RateStatus>(RateStatus.OK)
    val customPerMileFareColor = dependentLiveData(customPerMileFareStatus, isUsingSuggestedRates) {
        if(isUsingSuggestedRates.value == true) {
            R.color.trolley_grey
        } else if (customPerMileFareStatus.value == RateStatus.WARNING) {
            R.color.red_500
        } else {
            R.color.blue_500
        }
    }

    val suggestedColor = isUsingSuggestedRates.map { if (!it) R.color.blue_500 else R.color.trolley_grey }
    val customColor = isUsingSuggestedRates.map { if (!it) R.color.trolley_grey else R.color.blue_500 }

    val warningMessage = dependentLiveData(suggestedMinimumFareStatus,
                                                   customMinimumFareStatus,
                                                   suggestedBaseFareStatus,
                                                   customBaseFareStatus,
                                                   suggestedPerMinuteFareStatus,
                                                   customPerMinuteFareStatus,
                                                   suggestedPerMileFareStatus,
                                                   customPerMileFareStatus,
                                                   isUsingSuggestedRates,
    ) {
        if(isUsingSuggestedRates.value == false && (suggestedMinimumFareStatus.value == RateStatus.WARNING ||
            customMinimumFareStatus.value == RateStatus.WARNING ||
            suggestedBaseFareStatus.value == RateStatus.WARNING ||
            customBaseFareStatus.value == RateStatus.WARNING ||
            suggestedPerMinuteFareStatus.value == RateStatus.WARNING ||
            customPerMinuteFareStatus.value == RateStatus.WARNING ||
            suggestedPerMileFareStatus.value == RateStatus.WARNING ||
            customPerMileFareStatus.value == RateStatus.WARNING)) {
                R.string.rate_warning
        } else {
            0
        }

    }


    init {
        viewModelScope.launch {
            rateCardModel.ratesFlow.combine(rateCardModel.indexFlow) { rates, index ->
                if (index == 1) {
                    rates.standardRateCard
                } else {
                    rates.premiumRateCard
                }
            }.collect { rateCard ->
                suggestedMinimumFare.value = beautifyNumber(rateCard.suggestedRates.minimum)
                customMinimumFare.value = beautifyNumber(rateCard.customRates.minimum)
                if(rateCardModel.shouldWarn(rateCard.customRates.minimum, rateCard.suggestedRates.minimum)) {
                    customMinimumFareStatus.value = RateStatus.WARNING
                } else {
                    customMinimumFareStatus.value = RateStatus.OK
                }
                suggestedBaseFare.value = beautifyNumber(rateCard.suggestedRates.base)
                customBaseFare.value = beautifyNumber(rateCard.customRates.base)
                if(rateCardModel.shouldWarn(rateCard.customRates.base, rateCard.suggestedRates.base)) {
                    customBaseFareStatus.value = RateStatus.WARNING
                } else {
                    customBaseFareStatus.value = RateStatus.OK
                }
                suggestedPerMinuteFare.value = beautifyNumber(rateCard.suggestedRates.perMinute)
                customPerMinuteFare.value = beautifyNumber(rateCard.customRates.perMinute)
                if(rateCardModel.shouldWarn(rateCard.customRates.perMinute, rateCard.suggestedRates.perMinute)) {
                    customPerMinuteFareStatus.value = RateStatus.WARNING
                } else {
                    customPerMinuteFareStatus.value = RateStatus.OK
                }
                suggestedPerMileFare.value = beautifyNumber(rateCard.suggestedRates.perMile)
                customPerMileFare.value = beautifyNumber(rateCard.customRates.perMile)
                if(rateCardModel.shouldWarn(rateCard.customRates.perMile, rateCard.suggestedRates.perMile)) {
                    customPerMileFareStatus.value = RateStatus.WARNING
                } else {
                    customPerMileFareStatus.value = RateStatus.OK
                }
                isUsingSuggestedRates.value = rateCard.useSuggestedRates
            }
        }


    }

    fun increaseRate(type: RateType) {
        isSubmitEnabled.value = true
        when(type){
            RateType.MINIMUM    -> {
                customMinimumFare.value?.let {
                    val incremented = rateCardModel.incrementRate(parseStringToNumber(it))
                    customMinimumFare.value = beautifyNumber(incremented)
                    suggestedMinimumFare.value?.let { suggested ->
                        val parsedSuggested = parseStringToNumber(suggested)
                        if(rateCardModel.shouldWarn(incremented, parsedSuggested)) {
                            customMinimumFareStatus.value = RateStatus.WARNING
                        } else {
                            customMinimumFareStatus.value = RateStatus.OK
                        }
                    }
                }
            }
            RateType.BASE       -> {
                customBaseFare.value?.let {
                    val incremented = rateCardModel.incrementRate(parseStringToNumber(it))
                    customBaseFare.value = beautifyNumber(incremented)
                    suggestedBaseFare.value?.let { suggested ->
                        val parsedSuggested = parseStringToNumber(suggested)
                        if(rateCardModel.shouldWarn(incremented, parsedSuggested)) {
                            customBaseFareStatus.value = RateStatus.WARNING
                        } else {
                            customBaseFareStatus.value = RateStatus.OK
                        }
                    }
                }
            }
            RateType.PER_MINUTE -> {
                customPerMinuteFare.value?.let {
                    val incremented = rateCardModel.incrementRate(parseStringToNumber(it))
                    customPerMinuteFare.value = beautifyNumber(incremented)
                    suggestedPerMinuteFare.value?.let { suggested ->
                        val parsedSuggested = parseStringToNumber(suggested)
                        if(rateCardModel.shouldWarn(incremented, parsedSuggested)) {
                            customPerMinuteFareStatus.value = RateStatus.WARNING
                        } else {
                            customPerMinuteFareStatus.value = RateStatus.OK
                        }
                    }
                }
            }
            RateType.PER_MILE   -> {
                customPerMileFare.value?.let {
                    val incremented = rateCardModel.incrementRate(parseStringToNumber(it))
                    customPerMileFare.value = beautifyNumber(incremented)
                    suggestedPerMileFare.value?.let { suggested ->
                        val parsedSuggested = parseStringToNumber(suggested)
                        if(rateCardModel.shouldWarn(incremented, parsedSuggested)) {
                            customPerMileFareStatus.value = RateStatus.WARNING
                        } else {
                            customPerMileFareStatus.value = RateStatus.OK
                        }
                    }
                }
            }
        }
    }

    fun decreaseRate(type: RateType) {
        isSubmitEnabled.value = true
        when(type){
            RateType.MINIMUM    -> {
                customMinimumFare.value?.let {
                    val decremented = rateCardModel.decrementRate(parseStringToNumber(it))
                    customMinimumFare.value = beautifyNumber(decremented)
                    suggestedMinimumFare.value?.let { suggested ->
                        val parsedSuggested = parseStringToNumber(suggested)
                        if(rateCardModel.shouldWarn(decremented, parsedSuggested)) {
                            customMinimumFareStatus.value = RateStatus.WARNING
                        } else {
                            customMinimumFareStatus.value = RateStatus.OK
                        }
                    }
                }
            }
            RateType.BASE       -> {
                customBaseFare.value?.let {
                    val decremented = rateCardModel.decrementRate(parseStringToNumber(it))
                    customBaseFare.value = beautifyNumber(decremented)
                    suggestedBaseFare.value?.let { suggested ->
                        val parsedSuggested = parseStringToNumber(suggested)
                        if(rateCardModel.shouldWarn(decremented, parsedSuggested)) {
                            customBaseFareStatus.value = RateStatus.WARNING
                        } else {
                            customBaseFareStatus.value = RateStatus.OK
                        }
                    }
                }
            }
            RateType.PER_MINUTE -> {
                customPerMinuteFare.value?.let {
                    val decremented = rateCardModel.decrementRate(parseStringToNumber(it))
                    customPerMinuteFare.value = beautifyNumber(decremented)
                    suggestedPerMinuteFare.value?.let { suggested ->
                        val parsedSuggested = parseStringToNumber(suggested)
                        if(rateCardModel.shouldWarn(decremented, parsedSuggested)) {
                            customPerMinuteFareStatus.value = RateStatus.WARNING
                        } else {
                            customPerMinuteFareStatus.value = RateStatus.OK
                        }
                    }
                }
            }
            RateType.PER_MILE   -> {
                customPerMileFare.value?.let {
                    val decremented = rateCardModel.decrementRate(parseStringToNumber(it))
                    customPerMileFare.value = beautifyNumber(decremented)
                    suggestedPerMileFare.value?.let { suggested ->
                        val parsedSuggested = parseStringToNumber(suggested)
                        if(rateCardModel.shouldWarn(decremented, parsedSuggested)) {
                            customPerMileFareStatus.value = RateStatus.WARNING
                        } else {
                            customPerMileFareStatus.value = RateStatus.OK
                        }
                    }
                }
            }
        }
    }

    fun suggestedClicked() {
        isSubmitEnabled.value = true
        isUsingSuggestedRates.value = true
    }

    fun customClicked() {
        isSubmitEnabled.value = true
        isUsingSuggestedRates.value = false
    }

    fun switchClicked() {
        isSubmitEnabled.value = true
    }

    fun submitClicked() {
        isSubmitEnabled.value = false

        isUsingSuggestedRates.value?.let { isUsingSuggestedRatesSafe ->
            customMinimumFare.value?.let { customMinimumFareSafe ->
                customBaseFare.value?.let { customBaseFareSafe ->
                    customPerMinuteFare.value?.let { customPerMinuteFareSafe ->
                        customPerMileFare.value?.let { customPerMileFareSafe ->
                            rateCardModel.updateValues(
                                isUsingSuggestedRatesSafe,
                                parseStringToNumber(customMinimumFareSafe),
                                parseStringToNumber(customBaseFareSafe),
                                parseStringToNumber(customPerMinuteFareSafe),
                                parseStringToNumber(customPerMileFareSafe)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun parseStringToNumber(toParse: String): Int {
        val parsed = numberFormat.parse(toParse)
        val parsedBD = parsed?.let{ BigDecimal(it.toString()) } ?: BigDecimal(0)
        val parsedInt = parsedBD.movePointRight(2).toInt()
        return parsedInt
    }

    private fun beautifyNumber(toBeautify: Int): String {
        return numberFormat.format( BigDecimal(toBeautify).movePointLeft(2))
    }

}

