package bridge.device;

public class ProjectorDevice implements Device{
    @Override
    public String applySettings(boolean powerOn, int volume) {
        String powerState = powerOn ? "ON" : "OFF";

        return "PROJECTOR | power=" + powerState + " | volume=" + volume;
    }
}
