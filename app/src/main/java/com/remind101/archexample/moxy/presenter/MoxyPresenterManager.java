package com.remind101.archexample.moxy.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.remind101.archexample.presenters.BasePresenter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MoxyPresenterManager {
    private static MoxyPresenterManager instance;

    private final AtomicLong currentId;

    private final Cache<Long, MvpPresenter<?>> presenters;

    private MoxyPresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit) {

        // Генератор ID номеров
        currentId = new AtomicLong();

        // Map'а для хранения Presenter'ов
        presenters = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();
    }

    public static MoxyPresenterManager getInstance() {
        if (instance == null) {
            instance = new MoxyPresenterManager(10, 30, TimeUnit.SECONDS);
        }
        return instance;
    }

    public <P extends MvpPresenter<?>> P restorePresenter(long presenterId) {
        P presenter = (P) presenters.getIfPresent(presenterId);
        presenters.invalidate(presenterId);
        return presenter;
    }

    public long savePresenter(MvpPresenter<?> presenter) {
        long presenterId = currentId.incrementAndGet();
        presenters.put(presenterId, presenter);
        return presenterId;
    }
}