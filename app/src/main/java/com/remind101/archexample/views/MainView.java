package com.remind101.archexample.views;

import com.remind101.archexample.models.Counter;

import java.util.List;

public interface MainView {
    void showCounters(List<Counter> counters);

    void showLoading();

    void showEmpty();
}
