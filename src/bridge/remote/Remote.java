package bridge.remote;

import bridge.device.Device;

import java.util.Objects;

public abstract class Remote {
    private final String id;
    private Device device;

    protected Remote(String id, Device device) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.device = Objects.requireNonNull(device, "Device cannot be null");
    }

    public String getId() {
        return id;
    }

    public void setImplementation(Device device) {
        this.device = Objects.requireNonNull(device, "Device cannot be nu;;");
    }

    protected String applySettings(int volume) {
        return device.applySettings(true, volume);
    }

    public abstract String execute();
}
