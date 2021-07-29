package com.htjy.baselibrary.livedata

import androidx.lifecycle.MutableLiveData


class IntLiveData : MutableLiveData<Int> {

    override fun getValue(): Int {
        return super.getValue() ?: 0
    }

    constructor() : super() {

    }
    constructor(value:Int) : super(value) {

    }
}