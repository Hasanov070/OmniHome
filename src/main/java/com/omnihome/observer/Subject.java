package com.omnihome.observer;


// Part 1 – Observer Pattern: Subject interface.
// Defines the contract for registering, removing, and notifying observers.

public interface Subject {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
