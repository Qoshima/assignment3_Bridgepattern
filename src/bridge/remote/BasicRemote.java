package bridge.remote;

import bridge.device.Device;

public class BasicRemote extends Remote{
     private static final int VOLUME_PRESET = 30;

     public BasicRemote(String id, Device device){
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
