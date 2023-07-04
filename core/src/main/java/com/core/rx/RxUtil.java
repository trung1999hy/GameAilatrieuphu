package com.core.rx;


import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {
    private RxUtil() {
    }

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static <T> Observable<T> appleHandlerStartFinish(Observable<T> src, final Runnable start, final Runnable finish) {
        return Observable.using(() -> {
                    if (start != null) {
                        start.run();
                    }
                    return null;
                },
                nothing -> src,
                resource -> {
                    if (finish != null) {
                        finish.run();
                    }
                });

    }
}
