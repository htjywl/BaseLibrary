package com.htjy.baselibrary.livedata

import androidx.lifecycle.MutableLiveData



class DoubleLiveData : MutableLiveData<Double> {
    override fun getValue(): Double {
        return super.getValue() ?: 0.0
    }

    constructor() : super() {

    }
    constructor(value:Double) : super(value) {

    }
}