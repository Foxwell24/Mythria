package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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

    public static class CPacketUpdatePlayerVelocityHandler implements IMessageHandler<CPacketUpdatePlayerVelocity, IMessage> {

        @Override
        public IMessage onMessage(final CPacketUpdatePlayerVelocity message, final MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            player.motionX = message.getMotionX();
            player.motionY = message.getMotionY();
            player.motionZ = message.getMotionZ();

            final EntityTracker e = player.getServerWorld().getEntityTracker();
            for (final EntityPlayer tracker : e.getTrackingPlayers(player)) {
                final EntityPlayerMP trackerMP = (EntityPlayerMP) tracker;
                trackerMP.connection.sendPacket(
                        new SPacketEntityVelocity(player.getEntityId(), player.motionX, player.motionY, player.motionZ));
            }
            return null;
        }

    }
}
