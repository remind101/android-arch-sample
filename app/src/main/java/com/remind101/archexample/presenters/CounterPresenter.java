package com.remind101.archexample.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.remind101.archexample.CounterDatabase;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.views.CounterView;

import static com.remind101.archexample.Utils.logIt;

@InjectViewState
public class CounterPresenter extends BasePresenter<Counter, CounterView> {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;
    private int position;

    @Override
    protected void updateView() {
        int value = CounterDatabase.getInstance().getCounter(position).getValue();
        getViewState().setCounterValue(value);
        getViewState().setMinusButtonEnabled(value > MIN_VALUE);
        getViewState().setPlusButtonEnabled(value < MAX_VALUE);
    }

    public void onMinusButtonClicked(int position) {
        model = CounterDatabase.getInstance().getCounter(position);
        model.setValue(model.getValue() - 1);
        CounterDatabase.getInstance().saveCounter(model);
        updateView();
    }

    public void onPlusButtonClicked(int position) {
        model = CounterDatabase.getInstance().getCounter(position);
        model.setValue(model.getValue() + 1);
        CounterDatabase.getInstance().saveCounter(model);
        updateView();
    }

    public void onCounterClicked(int position) {
        if (setupDone()) {
            getViewState().goToDetailView(model);
        }
    }

    public void onBindPosition(int position) {
        this.position = position;

        logIt("position=" +position+ ", hash=" + Integer.toString(this.hashCode()));
        model = CounterDatabase.getInstance().getCounter(position);
        getViewState().setCounterName(model.getName());
        getViewState().setCounterValue(model.getValue());

    }
}
