package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.GUI.MythriaConst;
import me.Jonathon594.Mythria.Managers.StatManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketProfileCreation implements IMessage {

    private NBTTagCompound toSend;

    public CPacketProfileCreation() {
    }

    public CPacketProfileCreation(final NBTTagCompound toSend) {
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

    public static class ProfileCreationPacketHandler implements IMessageHandler<CPacketProfileCreation, IMessage> {

        @Override
        public IMessage onMessage(final CPacketProfileCreation message, final MessageContext ctx) {
            final EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            final IProfile profile = serverPlayer.getCapability(ProfileProvider.PROFILE_CAP, null);

            if (profile.getCreated()) {
                serverPlayer.sendMessage(new TextComponentString(MythriaConst.ALREADY_PROFILE));
                return null;
            }

            profile.fromNBT(message.getToSend());
            profile.setPlayer(serverPlayer);

            StatManager.applyInitialStats(profile);
            profile.setCreated(true);
            return new ClientProfileDataCachePacket(profile.toNBT());
        }

    }
}
