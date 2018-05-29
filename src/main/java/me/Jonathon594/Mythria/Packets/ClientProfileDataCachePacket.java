package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    public static class ClientProfileDataCachePacketHandler implements IMessageHandler<ClientProfileDataCachePacket, IMessage> {

        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(final ClientProfileDataCachePacket message, final MessageContext ctx) {
            final IProfile p = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);
            p.fromNBT(message.getNbt());
            p.setPlayer(Minecraft.getMinecraft().player);
            MythriaUtil.updateClientGuiScreens();
            return null;
        }

    }
}
