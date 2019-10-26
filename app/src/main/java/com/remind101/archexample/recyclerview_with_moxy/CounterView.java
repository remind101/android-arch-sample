package com.remind101.archexample.recyclerview_with_moxy;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface CounterView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void setCounterName(String name);
    @StateStrategyType(SkipStrategy.class)
    void setCounterValue(int value);

    @StateStrategyType(SkipStrategy.class)
    void setMinusButtonEnabled(boolean enabled);
    @StateStrategyType(SkipStrategy.class)
    void setPlusButtonEnabled(boolean enabled);
}
