package com.htjy.baselibrary.livedata
import androidx.lifecycle.MutableLiveData



class BooleanLiveData : MutableLiveData<Boolean> {

    override fun getValue(): Boolean {
        return super.getValue() ?: false
    }

    constructor() : super() {

    }
    constructor(value:Boolean) : super(value) {

    }

}

