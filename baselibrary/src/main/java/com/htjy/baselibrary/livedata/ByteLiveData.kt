package com.htjy.baselibrary.livedata

import androidx.lifecycle.MutableLiveData



class ByteLiveData : MutableLiveData<Byte> {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }

    constructor() : super() {

    }
    constructor(value:Byte) : super(value) {

    }
}