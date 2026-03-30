package com.omnihome.factory;

// LuxuryLine concrete products

class LuxuryLight implements SmartLight {
    @Override
    public void turnOn() {
        System.out.println("[LuxuryLight]  Tempered-glass panel illuminating with adaptive colour.");
    }
}

class LuxuryLock implements SmartLock {
    @Override
    public void lock() {
        System.out.println("[LuxuryLock]   Face-ID verified. Titanium deadbolt secured.");
    }
}

class LuxuryThermostat implements SmartThermostat {
    @Override
    public void setTemperature(double celsius) {
        System.out.printf("[LuxuryThermostat] predicting comfort curve — setting %.1f°C instantly.%n", celsius);
    }
}

// ── LuxuryFactory
//  Concrete factory that produces the LuxuryLine device family

public class LuxuryFactory implements DeviceFactory {

    @Override
    public SmartLight createSmartLight() {
        return new LuxuryLight();
    }

    @Override
    public SmartLock createSmartLock() {
        return new LuxuryLock();
    }

    @Override
    public SmartThermostat createSmartThermostat() {
        return new LuxuryThermostat();
    }
}
