package com.remind101.archexample.presenters;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.remind101.archexample.CounterDatabase;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.views.MainView;

import java.util.List;

public class MainPresenter extends BasePresenter<List<Counter>, MainView> {
    private boolean isLoadingData = false;

    @Override
    protected void updateView() {
        // Business logic is in the presenter
        if (model.size() == 0) {
            view().showEmpty();
        } else {
            view().showCounters(model);
        }
    }

    @Override
    public void bindView(@NonNull MainView view) {
        super.bindView(view);

        // Let's not reload data if it's already here
        if (model == null && !isLoadingData) {
            view().showLoading();
            loadData();
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
