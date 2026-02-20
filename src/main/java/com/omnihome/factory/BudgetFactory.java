package com.omnihome.factory;

// ── BudgetLine concrete products ────────────────────────────────────────────

class BudgetLight implements SmartLight {
    @Override
    public void turnOn() {
        System.out.println("[BudgetLight]  Plastic light flickering on (120W bulb)");
    }
}

class BudgetLock implements SmartLock {
    @Override
    public void lock() {
        System.out.println("[BudgetLock]   Basic pin-lock engaged. No camera.");
    }
}

class BudgetThermostat implements SmartThermostat {
    @Override
    public void setTemperature(double celsius) {
        System.out.printf("[BudgetThermostat] please wait 49 min.%n", celsius);
    }
}

// ── BudgetFactory ────────────────────────────────────────────────────────────

/**
 * Concrete factory that produces the BudgetLine device family.
 */
public class BudgetFactory implements DeviceFactory {

    @Override
    public SmartLight createSmartLight() {
        return new BudgetLight();
    }

    @Override
    public SmartLock createSmartLock() {
        return new BudgetLock();
    }

    @Override
    public SmartThermostat createSmartThermostat() {
        return new BudgetThermostat();
    }
}
