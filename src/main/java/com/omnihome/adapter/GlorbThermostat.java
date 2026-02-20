package com.omnihome.adapter;

/**
 * Task 3 — The Adaptee.
 * Legacy 1990s thermostat that only understands Fahrenheit integers.
 * We cannot modify this class .
 */
public class GlorbThermostat {

    public void setHeatIndex(int fahrenheit) {
        System.out.printf("[GlorbThermostat] Legacy unit set to %d°F (done).%n", fahrenheit);
    }
}
