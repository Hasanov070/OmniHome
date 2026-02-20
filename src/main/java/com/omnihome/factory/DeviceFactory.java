package com.omnihome.factory;

/**
 * Task 2: Abstract Factory — defines the contract for creating device families.
 */
public interface DeviceFactory {
    SmartLight      createSmartLight();
    SmartLock       createSmartLock();
    SmartThermostat createSmartThermostat();
}
