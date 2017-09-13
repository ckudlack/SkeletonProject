package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.presenter.BasePresenter;
import com.cdk.skeletonproject.ui.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {
        void setUserList(List<SoundCloudUser> users);
    }

    interface Presenter extends BasePresenter {
        void buttonClicked(String userName, String clientId);
    }
}
