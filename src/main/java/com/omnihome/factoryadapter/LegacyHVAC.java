package com.omnihome.factoryadapter;

/**
 * Legacy HVAC controller — uses "applyTemp(int fahrenheit)" instead of
 * setTemperature(double celsius).
 */
public class LegacyHVAC {
    public void applyTemp(int fahrenheit) {
        System.out.printf("[LegacyHVAC] HVAC unit set to %d°F via applyTemp().%n", fahrenheit);
    }
}
