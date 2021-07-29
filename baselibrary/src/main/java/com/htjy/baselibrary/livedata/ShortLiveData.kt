package com.htjy.baselibrary.livedata

import androidx.lifecycle.MutableLiveData



class ShortLiveData : MutableLiveData<Short> {
    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
    constructor() : super() {

    }
    constructor(value:Short) : super(value) {

    }
}