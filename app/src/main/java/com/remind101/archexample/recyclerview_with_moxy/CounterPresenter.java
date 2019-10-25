package com.remind101.archexample.recyclerview_with_moxy;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.remind101.archexample.CounterDatabase;
import com.remind101.archexample.models.Counter;

@InjectViewState
public class CounterPresenter extends MvpPresenter<CounterView> {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 5;
    private Counter model;

    private void updateView(int value) {
        try {
            getViewState().setMinusButtonEnabled(value > MIN_VALUE);
            getViewState().setPlusButtonEnabled(value < MAX_VALUE);
            getViewState().setCounterValue(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMinusButtonClicked(int position) {
        try {
            model = CounterDatabase.getInstance().getCounter(position);
            int value = model.getValue();

            if(value > MIN_VALUE) {
                model.setValue(--value);
                model.setValue(value);
                CounterDatabase.getInstance().saveCounter(model);
                updateView(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPlusButtonClicked(int position) {
        try {
            model = CounterDatabase.getInstance().getCounter(position);
            int value = model.getValue();

            if(value < MAX_VALUE) {
                model.setValue(++value);
                CounterDatabase.getInstance().saveCounter(model);
                updateView(value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Moxy-методы onFirstViewAttach() и attachView(View view)
     * вызываются раньше и position для них не актуальна, поэтому
     * там её не нужно использовать
     */

    public void onBindPosition(int position) {

        model = CounterDatabase.getInstance().getCounter(position);
        getViewState().setCounterName(model.getName());
        updateView(model.getValue());
    }
}
