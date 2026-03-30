package com.omnihome.factoryadapter;

// Legacy deadbolt — uses proprietary "engage()" instead of lock().

public class LegacyDeadbolt {
    public void engage() {
        System.out.println("[LegacyDeadbolt] Mechanical bolt engaged by engage().");
    }
}
