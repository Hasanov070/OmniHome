package com.omnihome.observer;

///**
// Part 1 – Observer Pattern: Concrete Observer.
//
// SmartLights reacts to motion events by turning on automatically.
//
public class SmartLights implements Observer {

    private boolean on = false;

    @Override
    public void update() {
        on = true;
        System.out.println("[SmartLights] 💡 Motion detected — lights turned ON.");
    }

    public void turnOff() {
        on = false;
        System.out.println("[SmartLights] 💡 Lights turned OFF.");
    }

    public boolean isOn() {
        return on;
    }
}
