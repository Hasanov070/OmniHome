package com.omnihome.adapter;

/**
 * Task 3 — The Adapter.
 */
public class GlorbThermostat {

    public void setHeatIndex(int fahrenheit) {
        System.out.printf("[GlorbThermostat] Legacy unit set to %d°F (done).%n", fahrenheit);
    }
}
