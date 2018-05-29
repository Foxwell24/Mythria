package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketAddExperience implements IMessage {

    private MythicSkills type;

    private double value;

    public CPacketAddExperience() {
        // TODO Auto-generated constructor stub
    }

    public CPacketAddExperience(final MythicSkills type, final double value) {
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

    public static class CPacketAddExperienceHandler implements IMessageHandler<CPacketAddExperience, IMessage> {

        @Override
        public IMessage onMessage(final CPacketAddExperience message, final MessageContext ctx) {
            final IProfile profile = ctx.getServerHandler().player.getCapability(ProfileProvider.PROFILE_CAP, null);
            profile.addSkillExperience(message.getType(), message.getValue(), ctx.getServerHandler().player);
            return null;
        }

    }

    public static class SPacketUpdateExperienceHandler implements IMessageHandler<SPacketUpdateExperience, IMessage> {

        @Override
        public IMessage onMessage(final SPacketUpdateExperience message, final MessageContext ctx) {
            final IProfile profile = Minecraft.getMinecraft().player.getCapability(ProfileProvider.PROFILE_CAP, null);
            profile.getSkillLevels().put(message.getType(), message.getValue());
            return null;
        }

    }
}
