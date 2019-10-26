/**
 * Materials:
 * https://gist.github.com/senneco/ef2910a5b53aacdb053ebca21b10ef77
 *
 * and
 *
 * https://gist.github.com/AlexeyKorshun/abb20751b23d274aac86a63108094866
 */

package com.remind101.archexample.recyclerview_with_moxy;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.MvpDelegate;

public abstract class MvpViewHolder extends RecyclerView.ViewHolder {

    protected MvpDelegate mMvpDelegate;
    protected MvpDelegate mParentDelegate;

    public MvpViewHolder(MvpDelegate<?> parentDelegate, final View itemView) {
        super(itemView);
        mParentDelegate = parentDelegate;
    }

    @Nullable
    protected MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null && getMvpChildId() != null) {
            mMvpDelegate = new MvpDelegate<MvpViewHolder>(this);
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
