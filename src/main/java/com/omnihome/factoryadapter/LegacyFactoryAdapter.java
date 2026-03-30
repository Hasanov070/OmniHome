package com.omnihome.factoryadapter;

import com.omnihome.factory.DeviceFactory;
import com.omnihome.factory.SmartLight;
import com.omnihome.factory.SmartLock;
import com.omnihome.factory.SmartThermostat;

/**
 * Task: Factory Adapter — combines the Adapter and Abstract Factory patterns.
 * {@link LegacyDeviceLibrary}
 * {@link DeviceFactory}
 * {@link LegacyDeviceLibrary}
 */
public class LegacyFactoryAdapter implements DeviceFactory {

    private final LegacyDeviceLibrary library;

    public LegacyFactoryAdapter() {
        this.library = new LegacyDeviceLibrary();
    }

    /** Allow injecting a custom library instance (useful for testing). */
    public LegacyFactoryAdapter(LegacyDeviceLibrary library) {
        this.library = library;
    }

    // ── Product adapters (anonymous inner classes) ────────────────────────

    /**
     * Wraps {@link LegacyLamp} so it satisfies {@link SmartLight}.
     */
    @Override
    public SmartLight createSmartLight() {
        LegacyLamp lamp = library.spawnLamp();
        return () -> {
            System.out.print("[LegacyFactoryAdapter] SmartLight.turnOn() → ");
            lamp.illuminate();
        };
    }

    /**
     * Wraps {@link LegacyDeadbolt} so it satisfies {@link SmartLock}.
     */
    @Override
    public SmartLock createSmartLock() {
        LegacyDeadbolt deadbolt = library.spawnDeadbolt();
        return () -> {
            System.out.print("[LegacyFactoryAdapter] SmartLock.lock() → ");
            deadbolt.engage();
        };
    }

    /**
     * Wraps {@link LegacyHVAC} so it satisfies {@link SmartThermostat}.
     * Translates: setTemperature(double celsius) → applyTemp(int fahrenheit)
     * Conversion: °F = round((°C × 9/5) + 32)
     */
    @Override
    public SmartThermostat createSmartThermostat() {
        LegacyHVAC hvac = library.spawnHVAC();
        return celsius -> {
            int fahrenheit = (int) Math.round((celsius * 9.0 / 5.0) + 32);
            System.out.printf("[LegacyFactoryAdapter] SmartThermostat.setTemperature(%.1f°C) → ", celsius);
            hvac.applyTemp(fahrenheit);
        };
    }
}
