package com.remind101.archexample.presenters;

import com.remind101.archexample.models.Counter;
import com.remind101.archexample.views.CounterView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CounterPresenterTest {
    private CounterPresenter presenter;
    private CounterView view;
    private Counter counter;

    @Before
    public void setup() {
        presenter = spy(new CounterPresenter());
        view = mock(CounterView.class);
        presenter.bindView(view);
        counter = new Counter();
        counter.setId(4);
        counter.setName("My Counter");
        counter.setValue(18);
    }

    @Test
    public void testUpdateView_setsName() {
        presenter.setModel(counter);

        verify(view).setCounterName(eq("My Counter"));
    }

    @Test
    public void testUpdateView_setsValue() {
        presenter.setModel(counter);

        verify(view).setCounterValue(eq(18));
    }

    @Test
    public void testUpdateView_whenCounterGreaterThan0_setsMinusButtonEnabled() {
        presenter.setModel(counter);

        verify(view).setMinusButtonEnabled(eq(true));
    }

    @Test
    public void testUpdateView_whenCounterEqual0_setsMinusButtonDisabled() {
        counter.setValue(0);
        presenter.setModel(counter);

        verify(view).setMinusButtonEnabled(eq(false));
    }

    @Test
    public void testUpdateView_whenCounterLowerThan99_setsPlusButtonEnabled() {
        presenter.setModel(counter);

        verify(view).setPlusButtonEnabled(eq(true));
    }

    @Test
    public void testUpdateView_whenCounterEqual99_setsMinusButtonDisabled() {
        counter.setValue(99);
        presenter.setModel(counter);

        verify(view).setPlusButtonEnabled(eq(false));
    }

    @Test
    public void testOnMinusButtonClicked_whenCounterGreaterThan0_decrementsValue() {
        counter.setValue(16);
        presenter.setModel(counter);
        reset(view);

        presenter.onMinusButtonClicked();
        assertEquals(counter.getValue(), 15);
    }

    @Test
    public void testOnMinusButtonClicked_whenCounterEquals0_doesNotDoAnything() {
        counter.setValue(0);
        presenter.setModel(counter);
        reset(view);

        presenter.onMinusButtonClicked();
        assertEquals(counter.getValue(), 0);
    }

    @Test
    public void testOnPlusButtonClicked_whenCounterLowerThan99_incrementsValue() {
        counter.setValue(16);
        presenter.setModel(counter);
        reset(view);

        presenter.onPlusButtonClicked();
        assertEquals(counter.getValue(), 17);
    }

    @Test
    public void testOnPlusButtonClicked_whenCounterEquals99_doesNotDoAnything() {
        counter.setValue(99);
        presenter.setModel(counter);
        reset(view);

        presenter.onPlusButtonClicked();
        assertEquals(counter.getValue(), 99);
    }

    @Test
    public void testOnCounterClicked_opensDetailView() {
        presenter.setModel(counter);
        reset(view);

        presenter.onCounterClicked();
        verify(view).goToDetailView(eq(counter));
    }
}
