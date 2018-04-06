package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CPacketUpdatePlayerVelocity implements IMessage {
    private int entityID;
    private int motionX;
    private int motionY;
    private int motionZ;

    public CPacketUpdatePlayerVelocity() {
    }

    public CPacketUpdatePlayerVelocity(final Entity entityIn) {
        this(entityIn.getEntityId(), entityIn.motionX, entityIn.motionY, entityIn.motionZ);
    }

    public CPacketUpdatePlayerVelocity(final int entityIdIn, double motionXIn, double motionYIn, double motionZIn) {
        entityID = entityIdIn;

        if (motionXIn < -3.9D)
            motionXIn = -3.9D;

        if (motionYIn < -3.9D)
            motionYIn = -3.9D;

        if (motionZIn < -3.9D)
            motionZIn = -3.9D;

        if (motionXIn > 3.9D)
            motionXIn = 3.9D;

        if (motionYIn > 3.9D)
            motionYIn = 3.9D;

        if (motionZIn > 3.9D)
            motionZIn = 3.9D;

        motionX = (int) (motionXIn * 8000.0D);
        motionY = (int) (motionYIn * 8000.0D);
        motionZ = (int) (motionZIn * 8000.0D);
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        entityID = buf.readInt();
        motionX = buf.readShort();
        motionY = buf.readShort();
        motionZ = buf.readShort();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(entityID);
        buf.writeShort(motionX);
        buf.writeShort(motionY);
        buf.writeShort(motionZ);
    }

    public int getEntityID() {
        return entityID;
    }

    public double getMotionX() {
        return motionX / 8000.0D;
    }

    public double getMotionY() {
        return motionY / 8000.0D;
    }

    public double getMotionZ() {
        return motionZ / 8000.0D;
    }
}
