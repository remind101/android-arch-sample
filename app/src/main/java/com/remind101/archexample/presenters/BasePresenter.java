package com.remind101.archexample.presenters;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<M, V extends MvpView> extends MvpPresenter<V>{
    protected M model;
    protected V view;

    public void setModel(M model) {
        resetState();
        this.model = model;
        if (setupDone()) {
            updateView();
        }
    }

    protected void resetState() {
    }

    public void bindView(@NonNull V view) {
        this.view = view;
        if (setupDone()) {
            updateView();
        }
    }

    public void unbindView() {
        this.view = null;
    }

    protected V view() {
        return view;
    }

    protected abstract void updateView();

    protected boolean setupDone() {
        return view() != null && model != null;
    }
}
