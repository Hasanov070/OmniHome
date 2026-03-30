package com.omnihome.strategy;


// Part 2 – Strategy Pattern: Concrete Strategy — "SIREN" mode.

public class LoudSirenStrategy implements AlertStrategy {

    @Override
    public void executeAlert() {
        System.out.println("[SmartAlarm] SOUNDING 120dB SIREN!");
    }
}
