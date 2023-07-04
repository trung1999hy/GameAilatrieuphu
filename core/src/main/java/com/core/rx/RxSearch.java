package com.core.rx;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxSearch {

    public static Observable<String> fromView(EditText view) {

        final PublishSubject<String> subject = PublishSubject.create();

        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                subject.onNext(editable.toString());
            }
        });

        return subject;
    }
}
