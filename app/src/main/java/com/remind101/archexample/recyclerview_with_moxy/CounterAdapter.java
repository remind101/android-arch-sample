package com.remind101.archexample.recyclerview_with_moxy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpDelegate;
import com.remind101.archexample.R;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.recyclerview_with_moxy.CounterViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CounterAdapter extends RecyclerView.Adapter<CounterViewHolder> {

    protected final List<Counter> models;

    private MvpDelegate mParentDelegate;

    public CounterAdapter(MvpDelegate mParentDelegate) {
        this.mParentDelegate = mParentDelegate;
        models = new ArrayList<>();
    }

    // Списку требуется создать новый элемент
    @NonNull
    public CounterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CounterViewHolder(
                mParentDelegate,
                LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.counter_row,
                        parent,
                        false));
    }

    // Списку нужно заполнить данными элемент в позиции position. в Holder
    // передаем только position. По этому знаению его Presenter найдет
    // нужный Counter
    @Override
    public void onBindViewHolder(CounterViewHolder holder, int position) {
        holder.bindPosition(position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    // Обновить все содержимое списка
    public void clearAndAddAll(Collection<Counter> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }
}
