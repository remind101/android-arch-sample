package com.remind101.archexample;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpDelegate;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.presenters.CounterPresenter;
import com.remind101.archexample.views.CounterViewHolder;

public class CounterAdapter extends MvpRecyclerListAdapter<Counter, CounterPresenter, CounterViewHolder> {

    private MvpDelegate mParentDelegate;

    @Override
    public CounterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CounterViewHolder(mParentDelegate, LayoutInflater.from(parent.getContext()).inflate(R.layout.counter_row, parent, false));
    }

    @Override
    public void onBindViewHolder(CounterViewHolder holder, int position) {
        holder.setItemPosition(position);
        super.onBindViewHolder(holder, position);
    }

    public void init(MvpDelegate parentDelegate) {
        mParentDelegate = parentDelegate;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Counter model) {
        return model.getId();
    }
}
