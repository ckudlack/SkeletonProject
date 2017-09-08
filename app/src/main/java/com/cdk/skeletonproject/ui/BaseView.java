package com.cdk.skeletonproject.ui;

public interface BaseView {
    void showLoading();

    void hideLoading();

    void showError(Throwable error);
}
