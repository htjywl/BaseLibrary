package com.example.administrator

import androidx.lifecycle.MutableLiveData
import com.example.administrator.baseproject.TestBean
import com.htjy.baselibrary.base.BaseViewModel

/**
 * 文件描述：
 * 作者：jiangwei
 * 创建时间：2020/9/10
 * 更改时间：2020/9/10
 * 版本号：1
 */
class FragmentViewModel : BaseViewModel(){
    var loginResult = MutableLiveData<TestBean>()
    fun getData(){
        val testBean = TestBean()
        testBean.test1 = "测试1"
        loginResult.value = testBean
    }

    fun updateData(){
        val testBean = TestBean()
        testBean.test1 = "测试2"
        loginResult.value = testBean
    }

}