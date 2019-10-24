package com.remind101.archexample;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.remind101.archexample.presenters.BasePresenter;

public abstract class MvpViewHolder extends RecyclerView.ViewHolder {

    protected MvpDelegate mMvpDelegate;
    protected final MvpDelegate mParentDelegate;

    public MvpViewHolder(MvpDelegate<?> parentDelegate, final View itemView) {
        super(itemView);
        mParentDelegate = parentDelegate;
    }

    @Nullable
    protected MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null && getMvpChildId() != null) {
            mMvpDelegate = new MvpDelegate<>(this);
            mMvpDelegate.setParentDelegate(mParentDelegate, getMvpChildId());
        }
        return mMvpDelegate;
    }

    protected void destroyMvpDelegate() {
        if (getMvpDelegate() != null) {
            getMvpDelegate().onSaveInstanceState();
            getMvpDelegate().onDetach();
            mMvpDelegate = null;
        }
    }

    protected void createMvpDelegate() {
        if (getMvpDelegate() != null) {
            getMvpDelegate().onCreate();
            getMvpDelegate().onAttach();
            getMvpDelegate().onSaveInstanceState();
        }
    }


    protected abstract String getMvpChildId();

}
