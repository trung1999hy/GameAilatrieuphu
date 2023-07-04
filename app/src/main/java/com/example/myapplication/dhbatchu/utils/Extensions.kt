package com.example.myapplication.dhbatchu.utils

import android.view.View
import com.example.myapplication.dhbatchu.controller.AudioController
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

fun <V: View> V.clickWithAudio(debounceTime: Long = 600L, action: (view: V) -> Unit): Disposable {
    return RxView.clicks(this)
        .throttleFirst(debounceTime, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
        .subscribe { action(this)
            val audioController = AudioController(this.context){}
            audioController.playSoundClick()
        }
}