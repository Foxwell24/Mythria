package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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

    public static class CPacketUpdateFallDistanceHandler implements IMessageHandler<CPacketUpdateFallDistance, IMessage> {

        @Override
        public IMessage onMessage(final CPacketUpdateFallDistance message, final MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                final EntityLivingBase e = (EntityLivingBase) ctx.getServerHandler().player.getEntityWorld()
                        .getEntityByID(message.getEntityID());
                e.fallDistance = message.getFallDistance();
            });
            return null;
        }

    }
}
