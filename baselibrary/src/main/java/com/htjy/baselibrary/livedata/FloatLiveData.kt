package com.htjy.baselibrary.livedata

import androidx.lifecycle.MutableLiveData


class FloatLiveData : MutableLiveData<Float> {
    override fun getValue(): Float {
        return super.getValue()?: 0.0F
    }

    constructor() : super() {

    }
    constructor(value:Float) : super(value) {

    }
}