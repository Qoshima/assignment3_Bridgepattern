package bridge.remote;

import bridge.device.Device;

public class QuietRemote extends Remote{
    private static final int VOLUME_PRESET = 5;
    public QuietRemote(String id, Device device) {
        super(id, device);
    }

    @Override
    public String execute() {
        return applySettings(VOLUME_PRESET);
    }

    public int getVolumePreset() {
        return VOLUME_PRESET;
    }
}
