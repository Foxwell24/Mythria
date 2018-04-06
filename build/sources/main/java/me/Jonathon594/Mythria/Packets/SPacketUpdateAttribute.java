package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Enum.StatType;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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

}
