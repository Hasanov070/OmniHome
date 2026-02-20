package com.omnihome;

import com.omnihome.adapter.GlorbAdapter;
import com.omnihome.adapter.GlorbThermostat;
import com.omnihome.builder.AutomationRoutine;
import com.omnihome.builder.RoutineBuilder;
import com.omnihome.factory.*;
import com.omnihome.factoryadapter.*;
import com.omnihome.prototype.DeviceConfiguration;
import com.omnihome.singleton.CloudConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class OmniHomeTest {

    // ── Task 1: Singleton ────────────────────────────────────────────────────

    @Test
    @DisplayName("Singleton: same instance returned on repeated calls")
    void singleton_returnsSameInstance() {
        CloudConnection a = CloudConnection.getInstance();
        CloudConnection b = CloudConnection.getInstance();
        assertSame(a, b, "Both references must point to the same object");
    }

    @Test
    @DisplayName("Singleton: thread-safe across two concurrent threads")
    void singleton_threadSafe() throws InterruptedException {
        AtomicReference<CloudConnection> ref1 = new AtomicReference<>();
        AtomicReference<CloudConnection> ref2 = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            try { latch.await(); } catch (InterruptedException ignored) {}
            ref1.set(CloudConnection.getInstance());
        });
        Thread t2 = new Thread(() -> {
            try { latch.await(); } catch (InterruptedException ignored) {}
            ref2.set(CloudConnection.getInstance());
        });

        t1.start(); t2.start();
        latch.countDown();
        t1.join();  t2.join();

        assertSame(ref1.get(), ref2.get(), "Concurrent calls must return the same instance");
    }

    // ── Task 2: Abstract Factory ─────────────────────────────────────────────

    @Test
    @DisplayName("Factory: BudgetFactory creates correct types")
    void factory_budgetCreatesCorrectProducts() {
        DeviceFactory f = new BudgetFactory();
        assertNotNull(f.createSmartLight());
        assertNotNull(f.createSmartLock());
        assertNotNull(f.createSmartThermostat());
    }

    @Test
    @DisplayName("Factory: LuxuryFactory creates correct types")
    void factory_luxuryCreatesCorrectProducts() {
        DeviceFactory f = new LuxuryFactory();
        assertNotNull(f.createSmartLight());
        assertNotNull(f.createSmartLock());
        assertNotNull(f.createSmartThermostat());
    }

    @Test
    @DisplayName("Factory: BudgetFactory and LuxuryFactory produce distinct classes")
    void factory_familiesProduceDistinctClasses() {
        DeviceFactory budget  = new BudgetFactory();
        DeviceFactory luxury  = new LuxuryFactory();
        assertNotEquals(
            budget.createSmartLight().getClass(),
            luxury.createSmartLight().getClass()
        );
    }

    // ── Task 3: Adapter ──────────────────────────────────────────────────────

    /**
     * Captures fahrenheit passed to GlorbThermostat via a spy subclass.
     */
    static class GlorbSpy extends GlorbThermostat {
        int lastFahrenheit = Integer.MIN_VALUE;
        @Override public void setHeatIndex(int fahrenheit) { this.lastFahrenheit = fahrenheit; }
    }

    @Test
    @DisplayName("Adapter: 25°C converts to 77°F")
    void adapter_celsiusToFahrenheit_25() {
        GlorbSpy spy = new GlorbSpy();
        SmartThermostat adapter = new GlorbAdapter(spy);
        adapter.setTemperature(25.0);
        assertEquals(77, spy.lastFahrenheit);
    }

    @Test
    @DisplayName("Adapter: 0°C converts to 32°F")
    void adapter_celsiusToFahrenheit_freezing() {
        GlorbSpy spy = new GlorbSpy();
        new GlorbAdapter(spy).setTemperature(0.0);
        assertEquals(32, spy.lastFahrenheit);
    }

    @Test
    @DisplayName("Adapter: 100°C converts to 212°F")
    void adapter_celsiusToFahrenheit_boiling() {
        GlorbSpy spy = new GlorbSpy();
        new GlorbAdapter(spy).setTemperature(100.0);
        assertEquals(212, spy.lastFahrenheit);
    }

    @Test
    @DisplayName("Adapter: GlorbAdapter implements SmartThermostat")
    void adapter_implementsSmartThermostat() {
        SmartThermostat t = new GlorbAdapter(new GlorbThermostat());
        assertInstanceOf(SmartThermostat.class, t);
    }

    // ── Task 4: Builder ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Builder: builds routine with all fields set")
    void builder_buildsValidRoutine() {
        AutomationRoutine r = new RoutineBuilder()
                .withName("Vacation Mode")
                .addDevice("FrontDoor-Lock")
                .addDevice("LivingRoom-Light")
                .addDevice("Thermostat")
                .addDevice("SecurityCam")
                .atTime("23:00")
                .toggleNotification(true)
                .build();

        assertEquals("Vacation Mode", r.getName());
        assertEquals(4, r.getDevices().size());
        assertEquals("23:00", r.getTriggerTime());
        assertTrue(r.isSendNotification());
    }

    @Test
    @DisplayName("Builder: throws when name is missing")
    void builder_throwsWithoutName() {
        assertThrows(IllegalStateException.class, () ->
            new RoutineBuilder().addDevice("Light").atTime("08:00").build()
        );
    }

    @Test
    @DisplayName("Builder: throws when no devices added")
    void builder_throwsWithoutDevices() {
        assertThrows(IllegalStateException.class, () ->
            new RoutineBuilder().withName("Empty").atTime("08:00").build()
        );
    }

    @Test
    @DisplayName("Builder: throws when trigger time is missing")
    void builder_throwsWithoutTime() {
        assertThrows(IllegalStateException.class, () ->
            new RoutineBuilder().withName("NoTime").addDevice("Light").build()
        );
    }

    // ── Task 5: Prototype ────────────────────────────────────────────────────

    @Test
    @DisplayName("Prototype: clone produces a different object reference")
    void prototype_cloneIsDifferentObject() {
        DeviceConfiguration original = new DeviceConfiguration("192.168.1.10", 8883, "v3.4.1");
        DeviceConfiguration clone    = original.clone();
        assertNotSame(original, clone);
    }

    @Test
    @DisplayName("Prototype: changing clone IP does not affect original")
    void prototype_deepCopyIndependentIp() {
        DeviceConfiguration original = new DeviceConfiguration("192.168.1.10", 8883, "v3.4.1");
        DeviceConfiguration clone    = original.clone();
        clone.setIpAddress("192.168.1.99");

        assertEquals("192.168.1.10", original.getIpAddress(), "Original IP must be unchanged");
        assertEquals("192.168.1.99", clone.getIpAddress());
    }

    @Test
    @DisplayName("Prototype: clone preserves all field values")
    void prototype_clonePreservesFields() {
        DeviceConfiguration original = new DeviceConfiguration("10.0.0.5", 1883, "v2.1.0");
        DeviceConfiguration clone    = original.clone();

        assertEquals(original.getIpAddress(),       clone.getIpAddress());
        assertEquals(original.getPort(),            clone.getPort());
        assertEquals(original.getFirmwareVersion(), clone.getFirmwareVersion());
    }

    // ── Task 6: Factory Adapter ──────────────────────────────────────────────

    @Test
    @DisplayName("FactoryAdapter: implements DeviceFactory interface")
    void factoryAdapter_implementsDeviceFactory() {
        DeviceFactory fa = new LegacyFactoryAdapter();
        assertInstanceOf(DeviceFactory.class, fa);
    }

    @Test
    @DisplayName("FactoryAdapter: createSmartLight returns non-null SmartLight")
    void factoryAdapter_createsSmartLight() {
        DeviceFactory fa = new LegacyFactoryAdapter();
        SmartLight light = fa.createSmartLight();
        assertNotNull(light);
        assertInstanceOf(SmartLight.class, light);
    }

    @Test
    @DisplayName("FactoryAdapter: createSmartLock returns non-null SmartLock")
    void factoryAdapter_createsSmartLock() {
        DeviceFactory fa = new LegacyFactoryAdapter();
        SmartLock lock = fa.createSmartLock();
        assertNotNull(lock);
        assertInstanceOf(SmartLock.class, lock);
    }

    @Test
    @DisplayName("FactoryAdapter: createSmartThermostat returns non-null SmartThermostat")
    void factoryAdapter_createsSmartThermostat() {
        DeviceFactory fa = new LegacyFactoryAdapter();
        SmartThermostat t = fa.createSmartThermostat();
        assertNotNull(t);
        assertInstanceOf(SmartThermostat.class, t);
    }

    @Test
    @DisplayName("FactoryAdapter: thermostat converts 25°C → applyTemp(77°F)")
    void factoryAdapter_thermostatConvertsCelsiusToFahrenheit() {
        // Spy on LegacyHVAC via a spy subclass of the library
        int[] captured = {Integer.MIN_VALUE};

        LegacyDeviceLibrary spyLibrary = new LegacyDeviceLibrary() {
            @Override public LegacyHVAC spawnHVAC() {
                return new LegacyHVAC() {
                    @Override public void applyTemp(int fahrenheit) {
                        captured[0] = fahrenheit;
                    }
                };
            }
        };

        DeviceFactory fa = new LegacyFactoryAdapter(spyLibrary);
        fa.createSmartThermostat().setTemperature(25.0);
        assertEquals(77, captured[0], "25°C should translate to 77°F");
    }

    @Test
    @DisplayName("FactoryAdapter: thermostat converts 0°C → applyTemp(32°F)")
    void factoryAdapter_thermostatFreezingPoint() {
        int[] captured = {Integer.MIN_VALUE};
        LegacyDeviceLibrary spyLibrary = new LegacyDeviceLibrary() {
            @Override public LegacyHVAC spawnHVAC() {
                return new LegacyHVAC() {
                    @Override public void applyTemp(int fahrenheit) { captured[0] = fahrenheit; }
                };
            }
        };
        new LegacyFactoryAdapter(spyLibrary).createSmartThermostat().setTemperature(0.0);
        assertEquals(32, captured[0]);
    }

    @Test
    @DisplayName("FactoryAdapter: light delegates to LegacyLamp.illuminate()")
    void factoryAdapter_lightDelegatesToIlluminate() {
        boolean[] called = {false};
        LegacyDeviceLibrary spyLibrary = new LegacyDeviceLibrary() {
            @Override public LegacyLamp spawnLamp() {
                return new LegacyLamp() {
                    @Override public void illuminate() { called[0] = true; }
                };
            }
        };
        new LegacyFactoryAdapter(spyLibrary).createSmartLight().turnOn();
        assertTrue(called[0], "turnOn() must delegate to LegacyLamp.illuminate()");
    }

    @Test
    @DisplayName("FactoryAdapter: lock delegates to LegacyDeadbolt.engage()")
    void factoryAdapter_lockDelegatesToEngage() {
        boolean[] called = {false};
        LegacyDeviceLibrary spyLibrary = new LegacyDeviceLibrary() {
            @Override public LegacyDeadbolt spawnDeadbolt() {
                return new LegacyDeadbolt() {
                    @Override public void engage() { called[0] = true; }
                };
            }
        };
        new LegacyFactoryAdapter(spyLibrary).createSmartLock().lock();
        assertTrue(called[0], "lock() must delegate to LegacyDeadbolt.engage()");
    }

    @Test
    @DisplayName("FactoryAdapter: is interchangeable with LuxuryFactory as DeviceFactory")
    void factoryAdapter_isInterchangeableWithOtherFactories() {

        DeviceFactory[] factories = { new LuxuryFactory(), new LegacyFactoryAdapter() };
        for (DeviceFactory f : factories) {
            assertNotNull(f.createSmartLight(),      f.getClass().getSimpleName() + " light");
            assertNotNull(f.createSmartLock(),       f.getClass().getSimpleName() + " lock");
            assertNotNull(f.createSmartThermostat(), f.getClass().getSimpleName() + " thermostat");
        }
    }
}
