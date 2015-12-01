package com.remind101.archexample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.remind101.archexample.models.Counter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounterDatabase {
    private static CounterDatabase instance;

    private final Map<Integer, Counter> counters;

    private int nextId = 1;

    private CounterDatabase() {
        counters = new HashMap<>();
    }

    public static synchronized CounterDatabase getInstance() {
        if (instance == null) {
            instance = new CounterDatabase();
        }
        return instance;
    }

    public List<Counter> getAllCounters() {
        synchronized (counters) {
            return new ArrayList<>(counters.values());
        }
    }

    @Nullable public Counter getCounter(int id) {
        synchronized (counters) {
            return counters.get(id);
        }
    }

    public void saveCounter(@NonNull Counter counter) {
        synchronized (counters) {
            int id = nextId++;
            counter.setId(id);
            counters.put(id, counter);
        }
    }
}
