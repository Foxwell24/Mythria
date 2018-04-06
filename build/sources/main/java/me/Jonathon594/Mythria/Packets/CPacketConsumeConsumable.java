package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CPacketConsumeConsumable implements IMessage {

    private Consumable stat;

    private double value;

    public CPacketConsumeConsumable() {
        // TODO Auto-generated constructor stub
    }

    public CPacketConsumeConsumable(final Consumable consumable, final double value) {
        super();
        stat = consumable;
        this.value = value;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        stat = Consumable.values()[buf.readInt()];
        value = buf.readDouble();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(stat.ordinal());
        buf.writeDouble(value);
    }

    public Consumable getConsumable() {
        return stat;
    }

    public double getValue() {
        return value;
    }

}
