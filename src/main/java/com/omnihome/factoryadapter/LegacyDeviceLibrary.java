package com.omnihome.factoryadapter;

/**
 * Simulates a third-party / legacy device library from an acquired company.
 * Its factory methods use completely different names and return raw Objects
 * rather than our SmartDevice interfaces — making it incompatible with
 * {@link com.omnihome.factory.DeviceFactory}.
 *
 * We cannot modify this class (treat it as an external dependency).
 */
public class LegacyDeviceLibrary {

    /** Creates a legacy lighting unit (returns a raw Object, not SmartLight). */
    public LegacyLamp   spawnLamp()       { return new LegacyLamp(); }

    /** Creates a legacy door security unit. */
    public LegacyDeadbolt spawnDeadbolt() { return new LegacyDeadbolt(); }

    /** Creates a legacy HVAC controller (Fahrenheit-based). */
    public LegacyHVAC   spawnHVAC()      { return new LegacyHVAC(); }
}
