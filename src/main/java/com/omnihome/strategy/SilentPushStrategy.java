package com.omnihome.strategy;


// Part 2 – Strategy Pattern: Concrete Strategy — "SILENT" mode.

public class SilentPushStrategy implements AlertStrategy {

    @Override
    public void executeAlert() {
        System.out.println("[SmartAlarm] Sending silent push notification to homeowner's phone.");
    }
}
