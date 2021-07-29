package com.htjy.baselibrary.livedata

import androidx.lifecycle.MutableLiveData

class StringLiveData : MutableLiveData<String> {

    constructor() : super() {

    }
    constructor(value:String) : super(value) {

    }
    override fun getValue(): String {
        return super.getValue() ?: ""
    }




}