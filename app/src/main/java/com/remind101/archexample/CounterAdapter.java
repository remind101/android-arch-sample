package com.remind101.archexample;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.remind101.archexample.models.Counter;
import com.remind101.archexample.presenters.CounterPresenter;
import com.remind101.archexample.views.CounterViewHolder;

public class CounterAdapter extends MvpRecyclerListAdapter<Counter, CounterPresenter, CounterViewHolder> {
    @Override
    public CounterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CounterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.counter_row, parent, false));
    }

    @NonNull
    @Override
    protected CounterPresenter createPresenter(@NonNull Counter counter) {
        CounterPresenter presenter = new CounterPresenter();
        presenter.setModel(counter);
        return presenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Counter model) {
        return model.getId();
    }
}
