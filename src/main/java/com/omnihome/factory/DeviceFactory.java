package com.omnihome.factory;


// Task 2: Abstract Factory — Creating device families

public interface DeviceFactory {
    SmartLight      createSmartLight();
    SmartLock       createSmartLock();
    SmartThermostat createSmartThermostat();
}
