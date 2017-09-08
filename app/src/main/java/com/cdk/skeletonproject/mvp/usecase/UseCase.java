package com.cdk.skeletonproject.mvp.usecase;


import android.support.annotation.CallSuper;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * This is the "Interactor" of the MVP architecture
 */
public abstract class UseCase {

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @SuppressWarnings("unchecked")
    public void execute(Observable observable, Subscriber subscriber) {
        if (subscriptions == null || subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }

        final Subscription subscription = observable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        subscriptions.add(subscription);
    }

    @CallSuper
    public void clear() {
        subscriptions.clear();
    }
}
