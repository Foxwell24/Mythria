package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Attribute;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketUpdateAttribute implements IMessage {

    private Attribute stat;
    private int value;

    public SPacketUpdateAttribute() {
    }

    public SPacketUpdateAttribute(final Attribute stat, final int value) {
        super();
        this.stat = stat;
        this.value = value;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        stat = Attribute.values()[buf.readInt()];
        value = buf.readInt();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(stat.ordinal());
        buf.writeInt(value);
    }

    public Attribute getStat() {
        return stat;
    }

    public int getValue() {
        return value;
    }

    public static class SPacketUpdateAttributeHandler implements IMessageHandler<SPacketUpdateAttribute, IMessage> {

        @Override
        public IMessage onMessage(final SPacketUpdateAttribute message, final MessageContext ctx) {
            final IProfile profile = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);
            profile.getAttributes().put(message.getStat(), message.getValue());
            return null;
        }

    }
}
