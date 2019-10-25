package com.remind101.archexample;

import com.remind101.archexample.presenters.BasePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class MvpRecyclerListAdapter<M, P extends BasePresenter, VH extends MvpViewHolder> extends MvpRecyclerAdapter<M, P, VH> {

    protected final List<M> models;

    public MvpRecyclerListAdapter() {
        models = new ArrayList<>();
    }

    public void clearAndAddAll(Collection<M> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    public void addAll(Collection<M> data) {
        int oldSize = models.size();
        models.addAll(data);

        int addedSize = data.size();
        notifyItemRangeInserted(oldSize, addedSize);
    }

    public void addItem(M item) {
        models.add(item);
        notifyItemInserted(models.size());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    protected M getItem(int position) {
        return models.get(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
    }
}
