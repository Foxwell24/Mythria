package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.LockType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class Lock {
    private final UUID uuid;

    public void setLocation(BlockPos location) {
        this.location = location;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setLockPattern(int lockPattern) {
        this.lockPattern = lockPattern;
    }

    public void setType(LockType type) {
        this.type = type;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    private BlockPos location;

    public BlockPos getLocation() {
        return location;
    }

    public int getDimension() {
        return dimension;
    }

    public int getLockPattern() {
        return lockPattern;
    }

    public LockType getType() {
        return type;
    }

    public boolean isLocked() {
        return locked;
    }

    private int dimension;
    private int lockPattern;
    private LockType type;
    private boolean locked;

    public Lock(UUID uuid) {
        this.uuid = uuid;
    }

    public void loadData(NBTTagCompound compoundTag) {
        location = new BlockPos(compoundTag.getInteger("X"), compoundTag.getInteger("Y"), compoundTag.getInteger("Z"));
        dimension = compoundTag.getInteger("Dimension");
        lockPattern = compoundTag.getInteger("LockPattern");
        type = LockType.valueOf(compoundTag.getString("LockType").toUpperCase());
        locked = compoundTag.getBoolean("Locked");
    }

    public UUID getUUID() {
        return uuid;
    }

    public NBTTagCompound saveData() {
        NBTTagCompound compoundTag = new NBTTagCompound();
        compoundTag.setInteger("X", location.getX());
        compoundTag.setInteger("Y", location.getY());
        compoundTag.setInteger("Z", location.getZ());
        compoundTag.setInteger("Dimension", dimension);
        compoundTag.setInteger("LockPattern", lockPattern);
        compoundTag.setString("LockType", type.toString());
        compoundTag.setBoolean("Locked", locked);
        return compoundTag;
    }

    public void applyRandomLockPattern() {
        this.lockPattern = (int) (Math.random() * 1024);
    }
}
