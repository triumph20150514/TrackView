package com.trimph.demo.trackdemo.news;

import com.trimph.demo.trackdemo.base.BasePresenter;
import com.trimph.demo.trackdemo.base.BaseView;

/**
 * Created by tao on 2016/8/9.
 */

public interface NewsContract {

    /**
     * view 层
     */
    public interface newsView extends BaseView<newsPresener> {


    }

    /**
     * P 层
     */
    public interface newsPresener extends BasePresenter {

        void showDialog();
        void getNews();

    }

}
