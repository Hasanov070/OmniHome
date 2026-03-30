package com.omnihome.command;

import com.omnihome.observer.SmartAlarm;

/**
 * Part 3 – Command Pattern: Concrete Command.
 * Encapsulates the "arm alarm" request as an object.
 */
public class ArmAlarmCommand implements Command {

    private final SmartAlarm receiver;

    public ArmAlarmCommand(SmartAlarm alarm) {
        this.receiver = alarm;
    }

    @Override
    public void execute() {
        System.out.println("[ArmAlarmCommand] Executing: arm the alarm.");
        receiver.arm();
    }

    @Override
    public void undo() {
        System.out.println("[ArmAlarmCommand] Undoing: disarm the alarm.");
        receiver.disarm();
    }
}
