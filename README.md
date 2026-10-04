# Assignment 3 — Bridge Pattern

**Student:** Alimbekov Yernur  
**Group:** SE-2524  
**Topic:** D — Remote Controls  
**Repository:** https://github.com/Qoshima/assignment3_Bridgepattern  
**Base Commit:** `093b233f24d7ad7cd7027e111a0fa05b15e85408`

---

## 1. Project Description

This project demonstrates the **Bridge Design Pattern** using remote controls and devices.

The application contains two independently varying hierarchies:

- **Abstraction hierarchy:** remote controls
- **Implementation hierarchy:** devices

The `Remote` abstraction communicates with devices through the `Device` interface instead of depending directly on concrete device classes.

This allows remote-control types and device types to vary independently.

---

## 2. Bridge Role Map

| Bridge Role | Class | Source Path |
|---|---|---|
| Abstraction | `Remote` | `src/bridge/remote/Remote.java` |
| Refined Abstraction A1 | `BasicRemote` | `src/bridge/remote/BasicRemote.java` |
| Refined Abstraction A2 | `QuietRemote` | `src/bridge/remote/QuietRemote.java` |
| Implementor | `Device` | `src/bridge/device/Device.java` |
| Concrete Implementor I1 | `TvDevice` | `src/bridge/device/TvDevice.java` |
| Concrete Implementor I2 | `RadioDevice` | `src/bridge/device/RadioDevice.java` |
| Concrete Implementor I3 | `ProjectorDevice` | `src/bridge/device/ProjectorDevice.java` |
| Client | `Main` | `src/Main.java` |

---

## 3. Bridge Structure

The bridge between the abstraction hierarchy and the implementation hierarchy is stored inside the `Remote` class as an interface-typed reference:

```java
private Device device;
```

The implementation is supplied through the constructor:

```java
protected Remote(String id, Device device)
```

The implementation can be replaced at runtime using:

```java
public void setImplementation(Device device)
```

The abstraction exposes the following operation:

```java
public abstract String execute();
```

`BasicRemote` and `QuietRemote` implement `execute()` and delegate device-specific work through the `Device` interface.

### Important Code Locations

- Bridge field: `src/bridge/remote/Remote.java`
- `execute()` declaration: `src/bridge/remote/Remote.java`
- `BasicRemote.execute()`: `src/bridge/remote/BasicRemote.java`
- `QuietRemote.execute()`: `src/bridge/remote/QuietRemote.java`
- `setImplementation(...)`: `src/bridge/remote/Remote.java`
- Runtime switch check T5: `src/Main.java`, method `runT5()`

---

## 4. Application Behavior

### BasicRemote

`BasicRemote` powers on the selected device with volume:

```text
30
```

### QuietRemote

`QuietRemote` powers on the selected device with volume:

```text
5
```

Each device returns:

- device type
- power state
- resulting volume

Example:

```text
TV | power=ON | volume=30
```

The `Device` interface defines the low-level device operation:

```java
String applySettings(boolean powerOn, int volume);
```

Concrete device implementations provide their own output while keeping the same interface.

---

## 5. Runtime Implementation Switching

Test **T5** demonstrates that the implementation can be replaced at runtime without creating a new abstraction object.

A `BasicRemote` object is initially connected to a `TvDevice`.

```java
BasicRemote remote =
        new BasicRemote("BASIC-SWITCH", new TvDevice());
```

A second reference to the same object is stored:

```java
BasicRemote originalReference = remote;
```

The implementation is then replaced:

```java
remote.setImplementation(new RadioDevice());
```

Object identity is verified using reference equality:

```java
originalReference == remote
```

The test also verifies that the abstraction-side state remains unchanged:

- remote ID remains unchanged
- volume preset remains `30`
- the same `BasicRemote` object is used

Only the implementation reference changes.

Example result:

```text
before=TV | power=ON | volume=30
after=RADIO | power=ON | volume=30
```

Expected T5 state:

```text
sameObject=true
stateUnchanged=true
```

---

## 6. Independent Extension

The first working version of the project contained two device implementations:

- `TvDevice`
- `RadioDevice`

It also contained the complete abstraction hierarchy and runtime switching demonstrated by tests T1-T5.

The working version was committed with the following base commit:

```text
093b233f24d7ad7cd7027e111a0fa05b15e85408
```

After the base commit, a third implementation was added:

```text
ProjectorDevice
```

The existing classes were not modified:

- `Remote`
- `BasicRemote`
- `QuietRemote`
- `Device`
- `TvDevice`
- `RadioDevice`

The extension only required:

- adding `ProjectorDevice.java`
- updating `Main.java` to demonstrate T6 and T7
- updating `sources.txt`

The source changes between the base implementation and the final extension are recorded in:

```text
extension.diff
```

This demonstrates that a new implementation can be added without changing the existing abstraction hierarchy or existing implementations.

---

## 7. Build and Run

The project uses **Java JDK 17** and does not require external dependencies.

Compile the project from the project root:

```bash
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
```

Run the complete demonstration:

```bash
java -cp out Main --demo
```

---

## 8. Demonstration Checks

### T1 — A1 with I1

Classes:

```text
BasicRemote + TvDevice
```

Expected result:

```text
TV | power=ON | volume=30
```

---

### T2 — A1 with I2

Classes:

```text
BasicRemote + RadioDevice
```

Expected result:

```text
RADIO | power=ON | volume=30
```

---

### T3 — A2 with I1

Classes:

```text
QuietRemote + TvDevice
```

Expected result:

```text
TV | power=ON | volume=5
```

---

### T4 — A2 with I2

Classes:

```text
QuietRemote + RadioDevice
```

Expected result:

```text
RADIO | power=ON | volume=5
```

---

### T5 — Runtime Implementation Switch

Initial implementation:

```text
BasicRemote + TvDevice
```

Implementation after runtime replacement:

```text
BasicRemote + RadioDevice
```

Expected state:

```text
sameObject=true
stateUnchanged=true
```

Expected results:

```text
before=TV | power=ON | volume=30
after=RADIO | power=ON | volume=30
```

---

### T6 — A1 with I3

Classes:

```text
BasicRemote + ProjectorDevice
```

Expected result:

```text
PROJECTOR | power=ON | volume=30
```

---

### T7 — A2 with I3

Classes:

```text
QuietRemote + ProjectorDevice
```

Expected result:

```text
PROJECTOR | power=ON | volume=5
```

---

## 9. Expected Demo Output

```text
T1 PASS | BasicRemote + TvDevice | result=TV | power=ON | volume=30
T2 PASS | BasicRemote + RadioDevice | result=RADIO | power=ON | volume=30
T3 PASS | QuietRemote + TvDevice | result=TV | power=ON | volume=5
T4 PASS | QuietRemote + RadioDevice | result=RADIO | power=ON | volume=5
T5 PASS | BasicRemote: TvDevice -> RadioDevice | sameObject=true | stateUnchanged=true
   before=TV | power=ON | volume=30 | after=RADIO | power=ON | volume=30
T6 PASS | BasicRemote + ProjectorDevice | result=PROJECTOR | power=ON | volume=30
T7 PASS | QuietRemote + ProjectorDevice | result=PROJECTOR | power=ON | volume=5
SUMMARY: 7/7 PASS
```

The actual demonstration output is also stored in:

```text
demo-output.txt
```

---

## 10. Project Structure

```text
Assignment3_BridgePattern/
│
├── src/
│   ├── Main.java
│   │
│   └── bridge/
│       ├── device/
│       │   ├── Device.java
│       │   ├── TvDevice.java
│       │   ├── RadioDevice.java
│       │   └── ProjectorDevice.java
│       │
│       └── remote/
│           ├── Remote.java
│           ├── BasicRemote.java
│           └── QuietRemote.java
│
├── sources.txt
├── README.md
├── demo-output.txt
├── extension.diff
└── report.pdf
```

---

## 11. Why Bridge Is Used

The Bridge pattern is appropriate because the application contains two independent dimensions:

1. remote-control behavior
2. device implementation

The remote hierarchy contains:

```text
Remote
├── BasicRemote
└── QuietRemote
```

The device hierarchy contains:

```text
Device
├── TvDevice
├── RadioDevice
└── ProjectorDevice
```

Without Bridge, separate classes could be required for combinations such as:

```text
BasicTvRemote
BasicRadioRemote
BasicProjectorRemote
QuietTvRemote
QuietRadioRemote
QuietProjectorRemote
```

With Bridge, the two hierarchies are connected through composition.

For example:

```java
new BasicRemote("BASIC-01", new TvDevice());
```

and:

```java
new BasicRemote("BASIC-02", new ProjectorDevice());
```

use the same abstraction class with different implementations.

This prevents a class explosion and allows both dimensions to evolve independently.
