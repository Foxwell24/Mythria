package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ClientProfileDataCachePacket implements IMessage {

    private NBTTagCompound nbt;

    public ClientProfileDataCachePacket() {
    }

    public ClientProfileDataCachePacket(final NBTTagCompound nbt) {
        this.nbt = nbt;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeTag(buf, nbt);
    }

    public NBTTagCompound getNbt() {
        return nbt;
    }

}
