package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketUpdateConsumables implements IMessage {

    private Consumable consumable;
    private double value;

    public SPacketUpdateConsumables() {
    }

    public SPacketUpdateConsumables(final Consumable stat, final double value) {
        super();
        consumable = stat;
        this.value = value;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        consumable = Consumable.values()[buf.readInt()];
        value = buf.readDouble();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(consumable.ordinal());
        buf.writeDouble(value);
    }

    public Consumable getConsumable() {
        return consumable;
    }

    public double getValue() {
        return value;
    }

    public static class SPacketUpdateConsumablesHandler implements IMessageHandler<SPacketUpdateConsumables, IMessage> {

        @Override
        public IMessage onMessage(final SPacketUpdateConsumables message, final MessageContext ctx) {
            final IProfile profile = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);
            profile.setConsumable(message.getConsumable(), message.getValue());
            return null;
        }

    }
}
