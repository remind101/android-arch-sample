package com.remind101.archexample.views;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenterTag;
import com.remind101.archexample.MvpViewHolder;
import com.remind101.archexample.R;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.presenters.CounterPresenter;

import static com.remind101.archexample.Utils.logIt;

public class CounterViewHolder extends MvpViewHolder implements CounterView {
    private final View listItemView;
    private final TextView counterName;
    private final TextView counterValue;
    private final ImageView minusButton;
    private final ImageView plusButton;

    private int position = 0;

    private OnCounterClickListener listener;

    @InjectPresenter(type = PresenterType.GLOBAL)
    public CounterPresenter presenter;

    @ProvidePresenterTag(presenterClass = CounterPresenter.class, type = PresenterType.GLOBAL)
    String provideRepositoryPresenterTag() {
        String tag = Long.toString((long)hashCode() + System.currentTimeMillis());
        logIt(tag);
        return tag;
    }

    // Critical! Return this item unique id
    @Override
    protected String getMvpChildId() {
        return listItemView == null ? null : Integer.toString(listItemView.getId());
    }

    public CounterViewHolder(MvpDelegate mParentDelegate, View itemView) {
        super(mParentDelegate, itemView);

        listItemView = itemView;
        counterName = itemView.findViewById(R.id.counter_name);
        counterValue = itemView.findViewById(R.id.counter_value);
        minusButton = itemView.findViewById(R.id.minus_button);
        plusButton = itemView.findViewById(R.id.plus_button);

        createMvpDelegate();

        minusButton.setOnClickListener( view -> {
            presenter.onMinusButtonClicked(position);
        });

        plusButton.setOnClickListener( view -> {
            presenter.onPlusButtonClicked(position);
        });

        itemView.setOnClickListener(view -> {
            presenter.onCounterClicked(position);
        });
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

    @Override
    public void goToDetailView(Counter counter) {
        if (listener != null) {
            listener.onCounterClick(counter);
        }
    }

    public interface OnCounterClickListener {
        void onCounterClick(Counter counter);
    }

    public void bindPosition(int position) {
        this.position = position;
        presenter.onBindPosition(position);
    }
}
