package com.omnihome.singleton;


//Task 1: Thread-Safe Singleton using Initialization-on-Demand Holder idiom.

public class CloudConnection {

    private final String apiKey;
    private final String serverUrl;

    private CloudConnection() {
        this.apiKey    = "omni-secret-api-key-2026";
        this.serverUrl = "https://cloud.omnihome.io/v1";
        System.out.println("[CloudConnection] Connection established to " + serverUrl);
    }

    private static final class Holder {
        private static final CloudConnection INSTANCE = new CloudConnection();
    }

    public static CloudConnection getInstance() {
        return Holder.INSTANCE;
    }

    public String getApiKey()    { return apiKey; }
    public String getServerUrl() { return serverUrl; }

    @Override
    public String toString() {
        return "CloudConnection{url='" + serverUrl + "'}";
    }
}
