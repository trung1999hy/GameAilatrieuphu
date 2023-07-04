package com.core.rx;

import android.util.Log;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private Disposable busDisposable;

    public RxBus() {
    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Buser buser) {
        bus.onNext(buser);
    }

    public void registerBuser(OnMessageReceived onMessageReceived){
        busDisposable = bus.compose(RxUtil.applySchedulers()).subscribe(
                o -> {
                    if (o instanceof Buser) {
                        if (onMessageReceived != null) onMessageReceived.onMessageReceived((Buser) o);
                    }
                },throwable -> Log.e("RegisterBuser Error" ,throwable.getMessage())
        );
    }

    public void unRegisterBuser(){
        if (busDisposable != null) busDisposable.dispose();
    }

    public interface OnMessageReceived {
        void onMessageReceived(Buser buser);
    }

}