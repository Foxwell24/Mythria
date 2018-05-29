package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketUpdateExperience implements IMessage {

    private MythicSkills type;

    private double value;

    public SPacketUpdateExperience() {
        // TODO Auto-generated constructor stub
    }

    public SPacketUpdateExperience(final MythicSkills type, final double value) {
        super();
        this.type = type;
        this.value = value;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        type = MythicSkills.values()[buf.readInt()];
        value = buf.readDouble();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(type.ordinal());
        buf.writeDouble(value);
    }

    public MythicSkills getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

}
