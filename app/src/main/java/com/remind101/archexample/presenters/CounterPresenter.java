package com.remind101.archexample.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.remind101.archexample.CounterDatabase;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.views.CounterView;

@InjectViewState
public class CounterPresenter extends BasePresenter<Counter, CounterView> {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;

    @Override
    protected void updateView() {

        getViewState().setCounterName(model.getName());
        int value = model.getValue();
        getViewState().setCounterValue(value);
        getViewState().setMinusButtonEnabled(value > MIN_VALUE);
        getViewState().setPlusButtonEnabled(value < MAX_VALUE);
    }

    public void onMinusButtonClicked() {
        if (setupDone() && model.getValue() > MIN_VALUE) {
            model.setValue(model.getValue() - 1);
            CounterDatabase.getInstance().saveCounter(model);
            updateView();
        }
    }

    public void onPlusButtonClicked() {
        if (setupDone() && model.getValue() < MAX_VALUE) {
            model.setValue(model.getValue() + 1);
            CounterDatabase.getInstance().saveCounter(model);
            updateView();
        }
    }

    public void onCounterClicked() {
        if (setupDone()) {
            getViewState().goToDetailView(model);
        }
    }
}
