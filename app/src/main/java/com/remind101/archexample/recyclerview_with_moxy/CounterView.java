package com.remind101.archexample.recyclerview_with_moxy;

import com.arellomobile.mvp.MvpView;

public interface CounterView extends MvpView {
    void setCounterName(String name);

    void setCounterValue(int value);

    void setMinusButtonEnabled(boolean enabled);

    void setPlusButtonEnabled(boolean enabled);
}
