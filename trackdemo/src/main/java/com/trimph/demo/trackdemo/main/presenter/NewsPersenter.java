package com.trimph.demo.trackdemo.main.presenter;

import com.trimph.demo.trackdemo.base.BasePresenter;
import com.trimph.demo.trackdemo.base.BaseView;

/**
 * Created by tao on 2016/8/9.
 */

public interface NewsPersenter extends BasePresenter {

    public void showDialog();

    void loadData();

    void dimissDialog();

    void sendError();

}
