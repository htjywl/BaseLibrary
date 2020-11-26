package com.example.administrator.baseproject

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.administrator.baseproject.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.htjy.baselibrary.base.ViewModelActivity

class MainActivity : ViewModelActivity<ActivityMainBinding,MainViewModel>() {


    /*override fun setContentViewByBinding(layoutId: Int) {
        binding = getContentViewByBinding(layoutId)
    }*/


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initViews(savedInstanceState: Bundle?) {
        val iv = findViewById<ImageView>(R.id.iv_head)
        val tv_test1 = findViewById<TextView>(R.id.tv_test1)
        val tv_test2 = findViewById<TextView>(R.id.tv_test2)
        val frameLayout = findViewById<FrameLayout>(R.id.fl_content)
        val viewpager = findViewById<ViewPager>(R.id.viewpager)
        val mTabLayout = findViewById<View>(R.id.tab_layout) as TabLayout
        mTabLayout.addTab(mTabLayout.newTab().setText("test1"))
        mTabLayout.addTab(mTabLayout.newTab().setText("test2"))
        viewpager.adapter = TestFragmentAdapter(supportFragmentManager, "test1", "test2")
        mTabLayout.setupWithViewPager(viewpager)


        mBinding.viewModel = mViewModel
        /*showFragment(Test1Fragment.class);
        tv_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(Test1Fragment.class);
            }
        });

        tv_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(Test2Fragment.class);
            }
        });*/
        //ImageLoaderUtil.getInstance().loadCircleBorderImage("http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg",R.color.transparent
        //,iv,20,getResources().getColor(R.color.red));
        tv_test1.setOnClickListener {
            // ImageLoaderUtil.getInstance().loadCircleBorderImage("http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg",R.color.transparent
            //          ,iv,20,getResources().getColor(R.color.red));
        }
    }
    override fun initData() {
        mViewModel.getData()
        mBinding.tvTest.postDelayed({
            mViewModel.updateData()
        }, 1000)

    }
    override fun initListener() {}
}