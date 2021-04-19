package com.htjy.baselibrary.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
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
        registerUiChange()
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }
    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observeInActivity(this, Observer {
            showProgress(it)
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observeInActivity(this, Observer {
            hideProgress()
        })

        lifecycle.addObserver(mViewModel)
    }

    /**
     * 将非该Activity绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel){
        viewModels.forEach {viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observeInActivity(this, Observer {
                showProgress(it)
            })
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observeInActivity(this, Observer {
                hideProgress()
            })

            lifecycle.addObserver(viewModel)
        }
    }

    override fun initStateLayout() {}
    override fun showNullLayout() {}
    override fun showErrorLayout() {}
    override fun showSuccessLayout() {}
    override fun showNetWorkErrorLayout() {}


    companion object {
        private const val TAG = "MyMvpActivity"
    }
}