package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Managers.PerkManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketAddAttribute implements IMessage {

    private Perk pa;

    public SPacketAddAttribute() {
        // TODO Auto-generated constructor stub
    }

    public SPacketAddAttribute(final Perk pa) {
        super();
        this.pa = pa;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        final NBTTagCompound comp = ByteBufUtils.readTag(buf);
        pa = PerkManager.getAttribute(comp.getString("name"));
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        final NBTTagCompound comp = new NBTTagCompound();
        comp.setString("name", pa.getName());
        ByteBufUtils.writeTag(buf, comp);
    }

    public Perk getAttribute() {
        return pa;
    }

    public static class SPacketAddAttributeHandler implements IMessageHandler<SPacketAddAttribute, IMessage> {

        @Override
        public IMessage onMessage(final SPacketAddAttribute message, final MessageContext ctx) {
            final Minecraft mc = Minecraft.getMinecraft();
            final IProfile profile = mc.player.getCapability(ProfileProvider.PROFILE_CAP, null);
            profile.addAttribute(message.getAttribute());

            MythriaUtil.updateClientGuiScreens();
            return null;
        }

    }
}
