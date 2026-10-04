package bridge.device;

public class TvDevice implements Device{
    @Override
    public String applySettings(boolean powerOn, int volume) {
        String powerState = powerOn ? "ON" : "OFF";

        return "TV | power=" + powerState + " | volume=" + volume;
    }
}
