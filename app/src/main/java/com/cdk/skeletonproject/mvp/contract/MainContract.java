package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.presenter.BasePresenter;
import com.cdk.skeletonproject.ui.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {
        void setUserList(List<Artist> users);
        void startUserSelectionActivity();
        void startScanningService(List<Artist> artists);
        void registerEventBus();
        void unregisterEventBus();
        void startEventsActivity();
    }

    interface Presenter extends BasePresenter {
        void getDefaultUserFollowing(String clientId);
        void getDefaultUser(String clientId);
        void getEventsForArtist(String artist, String location, String apiKey);
        void dialogContinueClicked(List<Artist> artists);
        void onScanComplete();
    }
}
