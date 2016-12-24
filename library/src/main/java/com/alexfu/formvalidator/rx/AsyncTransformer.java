package com.alexfu.formvalidator.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A {@link rx.Observable.Transformer} that subscribes the observable on the
 * io thread and observes emissions on the main thread.
 */

class AsyncTransformer<T> implements Observable.Transformer<T,T> {
  @Override public Observable<T> call(Observable<T> observable) {
    return observable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
