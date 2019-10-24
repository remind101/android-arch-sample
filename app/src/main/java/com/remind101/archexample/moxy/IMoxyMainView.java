package com.remind101.archexample.moxy;

import com.arellomobile.mvp.MvpView;
import com.remind101.archexample.models.Counter;

import java.util.List;

public interface IMoxyMainView extends MvpView {

    void showCountersA(List<Counter> counters);


    void showLoading();


    void showEmpty();
}