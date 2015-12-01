package com.remind101.archexample.views;

import com.remind101.archexample.models.Counter;

public interface CounterView {
    void setCounterName(String name);

    void setCounterValue(int value);

    void setMinusButtonEnabled(boolean enabled);

    void setPlusButtonEnabled(boolean enabled);

    void goToDetailView(Counter counter);
}
