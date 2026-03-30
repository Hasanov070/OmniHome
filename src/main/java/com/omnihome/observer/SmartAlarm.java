package com.omnihome.observer;

import com.omnihome.strategy.AlertStrategy;
import com.omnihome.strategy.LoudSirenStrategy;
import com.omnihome.strategy.SilentPushStrategy;

import java.util.HashMap;
import java.util.Map;


//  Part 1 + Part 2 — Concrete Observer AND Strategy Context.

public class SmartAlarm implements Observer {

    private final Map<String, AlertStrategy> registry = new HashMap<>();

    private AlertStrategy currentStrategy;
    private boolean armed = false;

    public SmartAlarm() {
        // Populate the registry at construction time.
        registry.put("SILENT", new SilentPushStrategy());
        registry.put("SIREN",  new LoudSirenStrategy());

        // Default to SILENT.
        currentStrategy = registry.get("SILENT");
    }

    /**
     * @param key e.g. "SILENT" or "SIREN"
     * @throws IllegalArgumentException if the key is not registered
     */
    public void setStrategy(String key) {
        AlertStrategy strategy = registry.get(key);
        if (strategy == null) {
            throw new IllegalArgumentException("[SmartAlarm] Unknown strategy key: " + key);
        }
        currentStrategy = strategy;
        System.out.println("[SmartAlarm] Strategy swapped to: " + key);
    }

    @Override
    public void update() {
        System.out.println("[SmartAlarm]   Motion event received — executing alert strategy.");
        currentStrategy.executeAlert();
    }

    /** Arms the alarm (used by Command pattern). */
    public void arm() {
        armed = true;
        System.out.println("[SmartAlarm]  Alarm ARMED.");
    }

    /** Disarms the alarm (supports undo in Command pattern). */
    public void disarm() {
        armed = false;
        System.out.println("[SmartAlarm]  Alarm DISARMED.");
    }

    public boolean isArmed() {
        return armed;
    }
}
