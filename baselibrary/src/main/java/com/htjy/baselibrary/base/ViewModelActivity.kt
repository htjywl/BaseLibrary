package com.htjy.baselibrary.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.htjy.baselibrary.ext.getVmClazz


abstract class ViewModelActivity<VB : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {
    lateinit var mViewModel: VM
    lateinit var mBinding: VB


    @Suppress("UNCHECKED_CAST")
    override fun setContentViewByBinding(layoutId: Int) {
        mBinding = getContentViewByBinding(layoutId) as VB
    }

     override fun isBinding(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //如果你硬是不想用，这里就需要?号了
        if (isBinding){
            mBinding.lifecycleOwner = this
        }
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


    override fun initStateLayout() {}
    override fun showNullLayout() {}
    override fun showErrorLayout() {}
    override fun showSuccessLayout() {}
    override fun showNetWorkErrorLayout() {}
    override fun showProgress() {}
    override fun hideProgress() {}


    companion object {
        private const val TAG = "MyMvpActivity"
    }
}