package com.omnihome.observer;

import java.util.ArrayList;
import java.util.List;


// * Part 1 – Observer Pattern: Concrete Subject.
//  MotionSensor maintains a list of interested observers and pushes notifications to every one of them the moment motion is detected.

public class MotionSensor implements Subject {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
        System.out.println("[MotionSensor] Observer registered: " + o.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
        System.out.println("[MotionSensor] Observer removed: " + o.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    public void detectMotion() {
        System.out.println("[MotionSensor] ⚡ Motion detected! Broadcasting to " + observers.size() + " device(s)...");
        notifyObservers();
    }
}
