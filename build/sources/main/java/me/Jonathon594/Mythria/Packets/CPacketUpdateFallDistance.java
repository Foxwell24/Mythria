package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CPacketUpdateFallDistance implements IMessage {

    private int entityID;
    private float fallDistance;

    public CPacketUpdateFallDistance() {
        // TODO Auto-generated constructor stub
    }

    public CPacketUpdateFallDistance(final EntityLivingBase e) {
        super();
        entityID = e.getEntityId();
        fallDistance = e.fallDistance;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        entityID = buf.readInt();
        fallDistance = buf.readFloat();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(entityID);
        buf.writeFloat(fallDistance);
    }

    public int getEntityID() {
        return entityID;
    }

    public float getFallDistance() {
        return fallDistance;
    }

}
