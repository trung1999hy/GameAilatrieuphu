package com.core

import com.core.rx.RxBus

interface MvpView {
    //    void showLoading();
    //
    //    void hideLoading();
    //
    //    void showMessage(String message);
    //
    //    void showMessage(@StringRes int resId);
    //
    //    boolean isNetworkConnected();
    //
    //    void hideKeyboard();
    fun sendBuser(rxBus: RxBus, key: String?, values: Any?)

    fun registerBuser(rxBus: RxBus, onMessageReceived: RxBus.OnMessageReceived?)

    fun unRegisterBuser(rxBus: RxBus)
}