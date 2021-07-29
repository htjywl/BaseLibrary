package com.htjy.baselibrary.databinding

import androidx.databinding.ObservableField


class IntObservableField(value: Int = 0) : ObservableField<Int>(value) {

    override fun get(): Int {
        return super.get()!!
    }

}