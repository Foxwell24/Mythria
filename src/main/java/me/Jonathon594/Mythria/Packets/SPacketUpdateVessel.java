package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Vessel.VesselProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketUpdateVessel implements IMessage {

    private NBTTagCompound compound;
    private int player;
    private int slot;

    public SPacketUpdateVessel(NBTTagCompound compound, int player, int slot) {
        this.compound = compound;
        this.player = player;
        this.slot = slot;
    }

    public SPacketUpdateVessel() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        compound = ByteBufUtils.readTag(buf);
        player = buf.readInt();
        slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, compound);
        buf.writeInt(player);
        buf.writeInt(slot);
    }

    public int getSlot() {
        return slot;
    }

    public int getPlayer() {
        return player;
    }

    public NBTTagCompound getCompound() {
        return compound;
    }

    public static class SPacketUpdateVesselHandler implements IMessageHandler<SPacketUpdateVessel, IMessage> {
        @Override
        public IMessage onMessage(SPacketUpdateVessel message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity e = Minecraft.getMinecraft().world.getEntityByID(message.getPlayer());
                if (e instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) e;
                    player.inventory.getStackInSlot(message.getSlot()).getCapability(VesselProvider.VESSEL_CAP, null).fromNBT(message.getCompound());
                }
            });
            return null;
        }
    }
}
