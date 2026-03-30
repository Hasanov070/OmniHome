package com.omnihome;

import com.omnihome.adapter.GlorbAdapter;
import com.omnihome.adapter.GlorbThermostat;
import com.omnihome.builder.AutomationRoutine;
import com.omnihome.builder.RoutineBuilder;
import com.omnihome.command.ArmAlarmCommand;
import com.omnihome.command.SmartRemote;
import com.omnihome.command.TurnOnLightCommand;
import com.omnihome.factory.*;
import com.omnihome.factoryadapter.LegacyFactoryAdapter;
import com.omnihome.observer.MotionSensor;
import com.omnihome.observer.SmartAlarm;
import com.omnihome.observer.SmartLights;
import com.omnihome.prototype.DeviceConfiguration;
import com.omnihome.singleton.CloudConnection;


// OmniHome — Version 2
public class Main {

    public static void main(String[] args) {

        banner("TASK 1 — Singleton: CloudConnection");

        CloudConnection conn1 = CloudConnection.getInstance();
        CloudConnection conn2 = CloudConnection.getInstance();

        System.out.println("Instance 1 @ " + System.identityHashCode(conn1));
        System.out.println("Instance 2 @ " + System.identityHashCode(conn2));
        System.out.println("Same object? " + (conn1 == conn2));   // must be true


        banner("TASK 2 — Abstract Factory: LuxuryLine");

        DeviceFactory factory = new LuxuryFactory();   // swap to BudgetFactory anytime
        SmartLight      light      = factory.createSmartLight();
        SmartLock       lock       = factory.createSmartLock();
        SmartThermostat thermostat = factory.createSmartThermostat();

        light.turnOn();
        lock.lock();
        thermostat.setTemperature(22.0);


        banner("TASK 3 — Adapter: Glorb Legacy Thermostat");

        GlorbThermostat glorbLegacy = new GlorbThermostat();
        SmartThermostat glorb       = new GlorbAdapter(glorbLegacy);

        // System sends 25°C  adapter converts to 77°F for the legacy device
        glorb.setTemperature(25.0);
        glorb.setTemperature(0.0);   // freezing  ->  32°F
        glorb.setTemperature(100.0); // boiling   -> 212°F


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
        DeviceFactory legacyFactory = new LegacyFactoryAdapter();

        SmartLight      legacyLight  = legacyFactory.createSmartLight();
        SmartLock       legacyLock   = legacyFactory.createSmartLock();
        SmartThermostat legacyHvac   = legacyFactory.createSmartThermostat();

        // Client calls the exact same interface — the adapter handles translation:
        legacyLight.turnOn();           // illuminate()
        legacyLock.lock();              // engage()
        legacyHvac.setTemperature(21.0);//  applyTemp(70°F)

        System.out.println("\n[Demo] Same client code works for Luxury, Budget, AND Legacy families.");
        System.out.println("[Demo] The factory adapter makes LegacyDeviceLibrary a drop-in replacement.");


        banner("TASK 7 — Observer + Strategy: Event Bus & Dynamic Alerting");

        // Instantiate the sensor and the two smart devices.
        MotionSensor motionSensor = new MotionSensor();
        SmartLights  smartLights  = new SmartLights();
        SmartAlarm   smartAlarm   = new SmartAlarm();

        // Set the alarm strategy to SILENT via the O(1) Map registry.
        smartAlarm.setStrategy("SILENT");

        // Subscribe both devices to the sensor (Observer registration).
        motionSensor.addObserver(smartLights);
        motionSensor.addObserver(smartAlarm);

        // First detection — lights react, alarm sends a silent push.
        System.out.println("\n--- First motion event (strategy: SILENT) ---");
        motionSensor.detectMotion();

        // Swap strategy at runtime — no if/else, just a registry key.
        smartAlarm.setStrategy("SIREN");

        // Second detection — alarm now sounds the loud siren.
        System.out.println("\n--- Second motion event (strategy: SIREN) ---");
        motionSensor.detectMotion();


        banner("TASK 8 — Command Pattern: Smart Remote with Undo");

        SmartRemote remote = new SmartRemote();

        // Program the remote buttons (invoker knows only Command interface).
        remote.setCommand(0, new TurnOnLightCommand(smartLights));
        remote.setCommand(1, new ArmAlarmCommand(smartAlarm));

        // Press Button 0  lights on.  Press Button 1 → alarm armed.
        System.out.println();
        remote.pressButton(0);
        System.out.println();
        remote.pressButton(1);

        // Undo the very last action → alarm should disarm.
        System.out.println();
        remote.pressUndo();

        banner("OmniHome simulation complete ✓");
    }

    private static void banner(String title) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  " + title);
        System.out.println("╚════════════════════════════════════════╝");
    }
}
