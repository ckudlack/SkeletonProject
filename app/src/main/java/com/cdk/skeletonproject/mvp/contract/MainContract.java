package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.presenter.BasePresenter;
import com.cdk.skeletonproject.ui.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {
        void setUserList(List<SoundCloudUser> users);
        void startUserSelectionActivity();
        void setupUI(String username, String avatarUrl);
    }

    interface Presenter extends BasePresenter {
        void getDefaultUserFollowing(String clientId);
        void getDefaultUser(String clientId);
    }
}
