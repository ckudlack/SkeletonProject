package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.presenter.BasePresenter;
import com.cdk.skeletonproject.ui.BaseView;

import java.util.List;

public interface EventsContract {

    interface Presenter extends BasePresenter {
        void initialize(String apiKey);
    }

    interface View extends BaseView {
        void showEventsList(List<Artist> artists);
    }
}
