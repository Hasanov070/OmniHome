## Project Structure

```
OmniHome/
├── pom.xml
├── docs/
│   └── adapter-uml.svg          
└── src/
    ├── main/java/com/omnihome/
    │   ├── Main.java             
    │   ├── singleton/
    │   │   └── CloudConnection.java
    │   ├── factory/
    │   │   ├── SmartLight.java
    │   │   ├── SmartLock.java
    │   │   ├── SmartThermostat.java
    │   │   ├── DeviceFactory.java
    │   │   ├── BudgetFactory.java
    │   │   └── LuxuryFactory.java
    │   ├── adapter/
    │   │   ├── GlorbThermostat.java
    │   │   └── GlorbAdapter.java
    │   ├── builder/
    │   │   ├── AutomationRoutine.java
    │   │   └── RoutineBuilder.java
    │   └── prototype/
    │       └── DeviceConfiguration.java
    └── test/java/com/omnihome/
        └── OmniHomeTest.java    
```

---

# Compile + run all tests
mvn test

# Run the demo simulation
mvn package -q
java -jar target/omnihome-1.0.0.jar

