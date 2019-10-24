package com.remind101.archexample.moxy.presenter;

import android.os.AsyncTask;
import android.os.SystemClock;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.remind101.archexample.CounterDatabase;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.moxy.IMoxyMainView;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class MoxyMainPresenter extends MvpPresenter<IMoxyMainView> {

    private boolean isLoadingData = false;
    private List<Counter> model = new ArrayList<>();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        // Let's not reload data if it's already here
        if (model == null && !isLoadingData) {
            getViewState().showLoading();
            loadData();
        }
    }

    @Override
    public void attachView(IMoxyMainView view) {
        super.attachView(view);
    }

    public void setModel(List<Counter> model) {
        resetState();
        this.model = model;
        if (setupDone()) {
            updateView();
        }
    }

    private void resetState() {
    }

    private boolean setupDone() {
        return model != null;
    }

    private void updateView() {
        // Business logic is in the presenter
        if (model.size() == 0) {
            getViewState().showEmpty();
        } else {
            getViewState().showCounters(model);
        }
    }

    private void loadData() {
        isLoadingData = true;
        new LoadDataTask().execute();
    }

    public void onAddCounterClicked() {
        Counter counter = new Counter();
        counter.setName("New Counter");
        counter.setValue(0);

        // Update view immediately
        model.add(counter);
        CounterDatabase.getInstance().saveCounter(counter);
        updateView();
    }

    // It's OK for this class not to be static and to keep a reference to the Presenter, as this
    // is retained during orientation changes and is lightweight (has no activity/view reference)
    private class LoadDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(3000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setModel(CounterDatabase.getInstance().getAllCounters());
            isLoadingData = false;
        }
    }
}
