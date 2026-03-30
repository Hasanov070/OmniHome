package com.omnihome.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Task 4 — Builder for {@link AutomationRoutine}.
 *

 */
public class RoutineBuilder {

    private String       name;
    private List<String> devices          = new ArrayList<>();
    private String       triggerTime;
    private boolean      sendNotification = false;

    public RoutineBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RoutineBuilder addDevice(String deviceName) {
        this.devices.add(deviceName);
        return this;
    }

    public RoutineBuilder atTime(String triggerTime) {
        this.triggerTime = triggerTime;
        return this;
    }

    public RoutineBuilder toggleNotification(boolean enabled) {
        this.sendNotification = enabled;
        return this;
    }

    /**
     * Validates required fields and returns the immutable {@link AutomationRoutine}.
     *
     * @throws IllegalStateException if name or triggerTime is missing, or no devices added.
     */
    public AutomationRoutine build() {
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("Routine must have a name.");
        }
        if (triggerTime == null || triggerTime.isBlank()) {
            throw new IllegalStateException("Routine must have a trigger time.");
        }
        if (devices.isEmpty()) {
            throw new IllegalStateException("Routine must include at least one device.");
        }
        return new AutomationRoutine(name, new ArrayList<>(devices), triggerTime, sendNotification);
    }
}
