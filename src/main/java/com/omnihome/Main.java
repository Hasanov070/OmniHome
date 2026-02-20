package com.omnihome;

import com.omnihome.adapter.GlorbAdapter;
import com.omnihome.adapter.GlorbThermostat;
import com.omnihome.builder.AutomationRoutine;
import com.omnihome.builder.RoutineBuilder;
import com.omnihome.factory.*;
import com.omnihome.factoryadapter.LegacyFactoryAdapter;
import com.omnihome.prototype.DeviceConfiguration;
import com.omnihome.singleton.CloudConnection;

/**
 * OmniHome — Full Demo
 * */
public class Main {

    public static void main(String[] args) {

        banner("TASK 1 — Singleton: CloudConnection");

        CloudConnection conn1 = CloudConnection.getInstance();
        CloudConnection conn2 = CloudConnection.getInstance();

        System.out.println("Instance 1 @ " + System.identityHashCode(conn1));
        System.out.println("Instance 2 @ " + System.identityHashCode(conn2));
        System.out.println("Same object? " + (conn1 == conn2));   // must be true

        // ─────────────────────────────────────────────────────────────────────

        banner("TASK 2 — Abstract Factory: LuxuryLine");

        DeviceFactory factory = new LuxuryFactory();   // swap to BudgetFactory anytime
        SmartLight      light      = factory.createSmartLight();
        SmartLock       lock       = factory.createSmartLock();
        SmartThermostat thermostat = factory.createSmartThermostat();

        light.turnOn();
        lock.lock();
        thermostat.setTemperature(22.0);

        // ─────────────────────────────────────────────────────────────────────

        banner("TASK 3 — Adapter: Glorb Legacy Thermostat");

        GlorbThermostat glorbLegacy = new GlorbThermostat();
        SmartThermostat glorb       = new GlorbAdapter(glorbLegacy);

        // System sends 25°C — adapter converts to 77°F for the legacy device
        glorb.setTemperature(25.0);
        glorb.setTemperature(0.0);   // freezing  →  32°F
        glorb.setTemperature(100.0); // boiling   → 212°F

        // ─────────────────────────────────────────────────────────────────────

        banner("TASK 4 — Builder: Movie Night Routine");

        AutomationRoutine movieNight = new RoutineBuilder()
                .withName("Movie Night")
                .addDevice("LivingRoom-LuxuryLight")
                .addDevice("LivingRoom-LuxuryThermostat")
                .addDevice("LivingRoom-SmartTV")
                .addDevice("FrontDoor-LuxuryLock")
                .atTime("20:00")
                .toggleNotification(true)
                .build();

        System.out.println("Built routine: " + movieNight);

        // ─────────────────────────────────────────────────────────────────────

        banner("TASK 5 — Prototype: Clone Device Config");

        DeviceConfiguration livingRoomCfg = new DeviceConfiguration("192.168.1.10", 8883, "v3.4.1");
        DeviceConfiguration guestRoomCfg  = livingRoomCfg.clone();

        // Change only the clone's IP
        guestRoomCfg.setIpAddress("192.168.1.11");

        System.out.println("Original  → " + livingRoomCfg);
        System.out.println("Clone     → " + guestRoomCfg);
        System.out.println("Original IP unchanged? " + livingRoomCfg.getIpAddress().equals("192.168.1.10"));

        // ─────────────────────────────────────────────────────────────────────

        banner("TASK 6 — Factory Adapter: Legacy Device Library");

        // The LegacyDeviceLibrary has totally different method names (spawnLamp,
        // spawnDeadbolt, spawnHVAC) and non-standard conventions.
        // LegacyFactoryAdapter wraps it so the client code is IDENTICAL to Tasks 2.
        DeviceFactory legacyFactory = new LegacyFactoryAdapter();

        SmartLight      legacyLight  = legacyFactory.createSmartLight();
        SmartLock       legacyLock   = legacyFactory.createSmartLock();
        SmartThermostat legacyHvac   = legacyFactory.createSmartThermostat();

        // Client calls the exact same interface — the adapter handles translation:
        legacyLight.turnOn();           // → illuminate()
        legacyLock.lock();              // → engage()
        legacyHvac.setTemperature(21.0);// → applyTemp(70°F)

        System.out.println("\n[Demo] Same client code works for Luxury, Budget, AND Legacy families.");
        System.out.println("[Demo] The factory adapter makes LegacyDeviceLibrary a drop-in replacement.");

        banner("OmniHome simulation complete ✓");
    }

    private static void banner(String title) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  " + title);
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
