package com.omnihome.prototype;

// Task 5 — Prototype

/**
 * Implements {@link Cloneable} so device configurations can be duplicated
 * without knowing the concrete type.
 */
public class DeviceConfiguration implements Cloneable {

    private String ipAddress;
    private int    port;
    private String firmwareVersion;

    public DeviceConfiguration(String ipAddress, int port, String firmwareVersion) {
        this.ipAddress       = ipAddress;
        this.port            = port;
        this.firmwareVersion = firmwareVersion;
    }

    // ── Prototype method


    @Override
    public DeviceConfiguration clone() {
        try {
            return (DeviceConfiguration) super.clone();
        } catch (CloneNotSupportedException e) {
            // Cannot happen — we implement Cloneable
            throw new AssertionError("Clone failed", e);
        }
    }

    // ── Getters & setters

    public String getIpAddress()       { return ipAddress; }
    public void   setIpAddress(String ip) { this.ipAddress = ip; }

    public int    getPort()            { return port; }
    public void   setPort(int port)    { this.port = port; }

    public String getFirmwareVersion()              { return firmwareVersion; }
    public void   setFirmwareVersion(String version){ this.firmwareVersion = version; }

    @Override
    public String toString() {
        return String.format("DeviceConfig{ip='%s', port=%d, firmware='%s'}",
                             ipAddress, port, firmwareVersion);
    }
}
