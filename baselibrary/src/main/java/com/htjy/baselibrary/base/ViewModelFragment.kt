package com.htjy.baselibrary.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.htjy.baselibrary.base.BaseFragment
import com.htjy.baselibrary.base.BaseViewModel
import com.htjy.baselibrary.ext.getVmClazz

/**
 * 文件描述：
 * 作者：jiangwei
 * 创建时间：2020/9/10
 * 更改时间：2020/9/10
 * 版本号：1
 */
abstract class ViewModelFragment<VB : ViewDataBinding, VM : BaseViewModel> : BaseFragment() {
    lateinit var mViewModel: VM
    lateinit var mBinding: VB

    @Suppress("UNCHECKED_CAST")
    override fun setDataBinding(root: View?) {
        mBinding = getContentViewByBinding(root) as VB
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isBinding) {
            mBinding.lifecycleOwner = this
        }
    }


    override fun isBinding(): Boolean {
        return true
    }

    override fun showNullLayout() {

    }

    override fun showErrorLayout() {

    }

    override fun showSuccessLayout() {

    }

    override fun showNetWorkErrorLayout() {

    }

    override fun initBeforeInitView() {
        mViewModel = createViewModel()
    }


    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    companion object {
        private const val TAG = "MyViewModelFragment"
    }
}