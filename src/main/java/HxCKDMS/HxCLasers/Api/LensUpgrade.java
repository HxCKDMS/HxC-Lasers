package HxCKDMS.HxCLasers.Api;

public class LensUpgrade {
    private UpgradeType type;
    private int amount;

    public LensUpgrade(UpgradeType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public LensUpgrade(UpgradeType type) {
        this.type = type;
        this.amount = 0;
    }

    public UpgradeType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public enum UpgradeType{
        RANGE,
        POWER,
        TRANSPARENCY,
        ADVANCED
    }
}
