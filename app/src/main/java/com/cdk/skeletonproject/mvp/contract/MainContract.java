package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.mvp.presenter.BasePresenter;
import com.cdk.skeletonproject.ui.BaseView;

public interface MainContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        void buttonClicked(String userName, String clientId);
    }
}
