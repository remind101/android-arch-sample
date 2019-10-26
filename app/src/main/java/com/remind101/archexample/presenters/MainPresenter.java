package com.remind101.archexample.moxy.presenter;

import android.os.AsyncTask;
import android.os.SystemClock;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.remind101.archexample.CounterDatabase;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.presenters.IMainView;

import java.util.List;

@InjectViewState
public class MainPresenter extends MvpPresenter<IMainView> {

    private boolean isLoadingData = false;
    private List<Counter> model;

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
    public void attachView(IMainView view) {
        super.attachView(view);

        if (model == null && !isLoadingData) {
            getViewState().showLoading();
            loadData();
        }
    }

    private void setModel(List<Counter> model) {

        this.model = model;
        if (setupDone()) {
            updateView();
        }
    }

    private boolean setupDone() {
        return model != null;
    }

    private void updateView() {
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

    // ID нового Counter генерится здесь как очередной порядковый номер в массиве.
    // По этому ID Counter также сохраняется к кэше. Это для того, чтобы поддерживать
    // совпадение индекса в списке, индекса элемента в RecyclerView и ID в кэше.
    public void onAddCounterClicked() {
        Counter counter = new Counter();
        counter.setId(model.size());
        counter.setName("New Counter " + model.size());
        counter.setValue(0);

        model.add(model.size(), counter);
        CounterDatabase.getInstance().saveCounter(counter);
        updateView();
    }

    // It's OK for this class not to be static and to keep a reference to the Presenter, as this
    // is retained during orientation changes and is lightweight (has no activity/view reference)
    private class LoadDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getViewState().showMenu(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(3000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setModel(CounterDatabase.getInstance().getAllCounters());
            isLoadingData = false;
            getViewState().showMenu(true);
        }
    }
}
