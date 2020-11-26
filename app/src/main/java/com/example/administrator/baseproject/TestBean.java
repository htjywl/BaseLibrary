package com.example.administrator.baseproject;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 文件描述：
 * 作者：jiangwei
 * 创建时间：2020/9/10
 * 更改时间：2020/9/10
 * 版本号：1
 */
@Entity
public class TestBean {
    @Unique
    String test1;

    @Generated(hash = 1743133882)
    public TestBean(String test1) {
        this.test1 = test1;
    }

    @Generated(hash = 2087637710)
    public TestBean() {
    }

    public String getTest1() {
        return this.test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }
}
