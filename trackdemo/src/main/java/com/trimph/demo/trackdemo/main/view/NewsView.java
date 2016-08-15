package com.trimph.demo.trackdemo.main.view;

import com.trimph.demo.trackdemo.base.BaseView;
import com.trimph.demo.trackdemo.main.presenter.NewsPersenter;

/**
 * view 借口
 * Created by tao on 2016/8/9.
 */

public interface NewsView extends BaseView<NewsPersenter> {

    /**
     *
     */
    void getData();




}
