package HxCKDMS.HxCLasers.Api;

import java.util.UUID;

public interface ILaser {
    boolean isOn();
    UUID getUUID();
}
