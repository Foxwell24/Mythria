package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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

}
