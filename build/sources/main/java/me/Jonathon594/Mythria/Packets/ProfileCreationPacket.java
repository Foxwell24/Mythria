package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ProfileCreationPacket implements IMessage {

    private NBTTagCompound toSend;

    public ProfileCreationPacket() {
    }

    public ProfileCreationPacket(final NBTTagCompound toSend) {
        this.toSend = toSend;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        toSend = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeTag(buf, toSend);
        System.out.println(buf.readableBytes());
    }

    public NBTTagCompound getToSend() {
        return toSend;
    }

}
