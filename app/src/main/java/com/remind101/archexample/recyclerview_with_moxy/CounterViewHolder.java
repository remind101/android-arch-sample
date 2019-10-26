package com.remind101.archexample.recyclerview_with_moxy;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenterTag;
import com.remind101.archexample.R;

public class CounterViewHolder extends MvpViewHolder implements CounterView {
    private final View listItemView;
    private final TextView counterName;
    private final TextView counterValue;
    private final ImageView minusButton;
    private final ImageView plusButton;

    private int position = 0;

    @InjectPresenter(type = PresenterType.GLOBAL)
    public CounterPresenter presenter;

    /**
     * Требуется для того чтобы сгенерировать уникальный тэг для презентера данного MvpView,
     * то есть данного холдера. Если этого не сделать, то у всех холдеров будет один и тот же инстанс
     * презентера и он будет ОДНОВРЕМЕННО обновлять их все ОДИНАКОВЫМИ данными ))
     *
     * https://github.com/Arello-Mobile/Moxy/wiki/Provides-Presenter-and-its-Tag
     */
    @ProvidePresenterTag(presenterClass = CounterPresenter.class, type = PresenterType.GLOBAL)
    String provideRepositoryPresenterTag() {
        return Long.toString((long)hashCode() + System.currentTimeMillis());
    }

    public CounterViewHolder(MvpDelegate mParentDelegate, View itemView) {
        super(mParentDelegate, itemView);

        listItemView = itemView;
        counterName = itemView.findViewById(R.id.counter_name);
        counterValue = itemView.findViewById(R.id.counter_value);
        minusButton = itemView.findViewById(R.id.minus_button);
        plusButton = itemView.findViewById(R.id.plus_button);

        getMvpDelegate().onCreate();
        getMvpDelegate().onAttach();
    }

    @Override
    public void setCounterName(String name) {
        counterName.setText(name);
    }

    @Override
    public void setCounterValue(int value) {
        counterValue.setText(String.valueOf(value));
    }

    @Override
    public void setMinusButtonEnabled(boolean enabled) {
        minusButton.getDrawable().setTint(enabled ? Color.BLACK : Color.GRAY);
        minusButton.setClickable(enabled);
    }

    @Override
    public void setPlusButtonEnabled(boolean enabled) {
        plusButton.getDrawable().setTint(enabled ? Color.BLACK : Color.GRAY);
        plusButton.setClickable(enabled);
    }

    public void bindPosition(int position) {
//        destroyMvpDelegate();
//        createMvpDelegate();

        this.position = position;
        presenter.onBindPosition(position);

        minusButton.setOnClickListener( view -> {
            presenter.onMinusButtonClicked(position);
        });

        plusButton.setOnClickListener( view -> {
            presenter.onPlusButtonClicked(position);
        });
    }

    // Critical! Return this item unique id
    @Override
    protected String getMvpChildId() {
        return listItemView == null ? null : Integer.toString(listItemView.getId());
    }
}
