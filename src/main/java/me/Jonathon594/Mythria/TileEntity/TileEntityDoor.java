package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Enum.LockType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDoor extends TileEntity {
    public int getLockPattern() {
        return LockPattern;
    }

    public void setLockPattern(int lockPattern) {
        LockPattern = lockPattern;
    }

    private int LockPattern = -1;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public LockType getType() {
        return type;
    }

    public void setType(LockType type) {
        this.type = type;
    }

    public boolean isBarred() {
        return barred;
    }

    public void setBarred(boolean barred) {
        this.barred = barred;
    }

    private boolean locked;
    private LockType type;
    private boolean barred;

    public TileEntityDoor() {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("LockPattern", LockPattern);
        compound.setBoolean("Locked", locked);
        compound.setString("LockType", type.toString().toUpperCase());
        compound.setBoolean("Barred", barred);

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        LockPattern = compound.getInteger("LockPattern");
        locked = compound.getBoolean("Locked");
        type = LockType.valueOf(compound.getString("LockType"));
        barred = compound.getBoolean("Barred");
        super.readFromNBT(compound);
    }

    public void toggledBarred() {
        this.barred = !barred;
    }
}
