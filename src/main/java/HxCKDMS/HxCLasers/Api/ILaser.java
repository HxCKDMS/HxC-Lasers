package HxCKDMS.HxCLasers.Api;

import java.util.UUID;

public interface ILaser {
    boolean isOn();
    boolean canPlaceLaser();
    UUID getUUID();
}
