package com.cren90.empower.empowerchallenge.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RateCardViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }
}