package com.omnihome.builder;

import java.util.Collections;
import java.util.List;

/**
 * Task 4 — Product built by {@link RoutineBuilder}.
 */
public class AutomationRoutine {

    private final String       name;
    private final List<String> devices;
    private final String       triggerTime;
    private final boolean      sendNotification;

    /** Package-private: only RoutineBuilder may construct this. */
    AutomationRoutine(String name, List<String> devices,
                      String triggerTime, boolean sendNotification) {
        this.name             = name;
        this.devices          = Collections.unmodifiableList(devices);
        this.triggerTime      = triggerTime;
        this.sendNotification = sendNotification;
    }

    public String       getName()             { return name; }
    public List<String> getDevices()          { return devices; }
    public String       getTriggerTime()      { return triggerTime; }
    public boolean      isSendNotification()  { return sendNotification; }

    @Override
    public String toString() {
        return String.format(
            "AutomationRoutine{name='%s', triggerTime='%s', notify=%b, devices=%s}",
            name, triggerTime, sendNotification, devices);
    }
}
