package com.omnihome.command;

import com.omnihome.observer.SmartLights;


// Part 3 – Command Pattern: Concrete Command
//  Encapsulates the "turn on lights" request as an object
//  The SmartRemote (invoker) never calls SmartLights directly


public class TurnOnLightCommand implements Command {

    private final SmartLights receiver;

    public TurnOnLightCommand(SmartLights lights) {
        this.receiver = lights;
    }

    @Override
    public void execute() {
        System.out.println("[TurnOnLightCommand] Executing: turn on lights.");
        receiver.update(); // reuses the same "on" path
    }

    @Override
    public void undo() {
        System.out.println("[TurnOnLightCommand] Undoing: turn off lights.");
        receiver.turnOff();
    }
}
