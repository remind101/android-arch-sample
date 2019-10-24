package com.remind101.archexample.moxy;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.remind101.archexample.CounterAdapter;
import com.remind101.archexample.R;
import com.remind101.archexample.models.Counter;
import com.remind101.archexample.moxy.presenter.MoxyMainPresenter;
import com.remind101.archexample.moxy.presenter.MoxyPresenterManager;

import java.util.List;

public class MoxyMainActivity extends MvpAppCompatActivity implements IMoxyMainView {

    // Нумерация слоев внутри ViewAnimator (FrameLayout)
    private static final int POSITION_LIST = 0;
    private static final int POSITION_LOADING = 1;
    private static final int POSITION_EMPTY = 2;

    private static final String SIS_KEY_PRESENTER_ID = "presenter_id";

    private ViewAnimator animator;
    private CounterAdapter adapter;

    @InjectPresenter
    public MoxyMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        animator = (ViewAnimator) findViewById(R.id.animator);
        RecyclerView recyclerView = (RecyclerView) animator.getChildAt(POSITION_LIST);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CounterAdapter();
        adapter.init(getMvpDelegate());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_counter:
                presenter.onAddCounterClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        long presenterId = MoxyPresenterManager.getInstance().savePresenter(presenter);
        outState.putLong(SIS_KEY_PRESENTER_ID, presenterId);
    }

    @Override
    public void showCounters(List<Counter> counters) {
        Log.e("APP_TAG", "showCountersA: " + counters.size());
        adapter.clearAndAddAll(counters);
        animator.setDisplayedChild(POSITION_LIST);
    }

    @Override
    public void showLoading() {
        animator.setDisplayedChild(POSITION_LOADING);
    }

    @Override
    public void showEmpty() {
        animator.setDisplayedChild(POSITION_EMPTY);
    }
}
