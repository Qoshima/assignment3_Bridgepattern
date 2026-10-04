package bridge.device;

public class RadioDevice implements Device{
    @Override
    public String applySettings(boolean powerOn, int volume) {
        String powerState = powerOn ? "ON" : "OFF";

        return "RADIO | power=" + powerState + " | volume=" + volume;
    }
}
