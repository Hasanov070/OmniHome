package com.omnihome.factoryadapter;

/**
 * Legacy lamp — uses proprietary "illuminate()" instead of turnOn().
 */
public class LegacyLamp {
    public void illuminate() {
        System.out.println("[LegacyLamp] Filament warming up.");
    }
}
