package com.remind101.archexample.views;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.remind101.archexample.MvpViewHolder;
import com.remind101.archexample.R;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.presenters.CounterPresenter;

public class CounterViewHolder extends MvpViewHolder<CounterPresenter> implements CounterView {
    private final TextView counterName;
    private final TextView counterValue;
    private final ImageView minusButton;
    private final ImageView plusButton;
    @Nullable private OnCounterClickListener listener;

    public CounterViewHolder(View itemView) {
        super(itemView);
        counterName = (TextView) itemView.findViewById(R.id.counter_name);
        counterValue = (TextView) itemView.findViewById(R.id.counter_value);
        minusButton = (ImageView) itemView.findViewById(R.id.minus_button);
        plusButton = (ImageView) itemView.findViewById(R.id.plus_button);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onMinusButtonClicked();
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPlusButtonClicked();
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCounterClicked();
            }
        });
    }

    public void setListener(@Nullable OnCounterClickListener listener) {
        this.listener = listener;
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
}
