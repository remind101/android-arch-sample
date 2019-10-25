package com.remind101.archexample.presenters;

import com.arellomobile.mvp.MvpPresenter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class PresenterManager {
    private static PresenterManager instance;

    private final AtomicLong currentId;

    private final Cache<Long, MvpPresenter<?>> presenters;

    private PresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit) {

        // Генератор ID номеров
        currentId = new AtomicLong();

        // Map'а для хранения Presenter'ов
        presenters = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager(10, 30, TimeUnit.SECONDS);
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