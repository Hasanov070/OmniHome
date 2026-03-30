package com.omnihome.adapter;

import com.omnihome.factory.SmartThermostat;

/**
 * Task 3 — The Adapter.
 */
public class GlorbAdapter implements SmartThermostat {

    private final GlorbThermostat glorbThermostat;

    public GlorbAdapter(GlorbThermostat glorbThermostat) {
        this.glorbThermostat = glorbThermostat;
    }

    @Override
    public void setTemperature(double celsius) {
        int fahrenheit = (int) Math.round((celsius * 9.0 / 5.0) + 32);
        System.out.printf("[GlorbAdapter]  Converting %.1f°C --> %d°F%n", celsius, fahrenheit);
        glorbThermostat.setHeatIndex(fahrenheit);
    }
}
