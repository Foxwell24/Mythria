package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Capability.Profile.IProfile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.HealthConditionInstance;
import me.Jonathon594.Mythria.Managers.HealthManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketAddHealthConditionInstance implements IMessage {

    private HealthConditionInstance pa;

    public SPacketAddHealthConditionInstance() {
        // TODO Auto-generated constructor stub
    }

    public SPacketAddHealthConditionInstance(final HealthConditionInstance pa) {
        super();
        this.pa = pa;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        final NBTTagCompound comp = ByteBufUtils.readTag(buf);
        pa = new HealthConditionInstance(HealthManager.getHealthConditionByName(comp.getString("Name")),
                comp.getDouble("Severity"));
        pa.setCureStep(comp.getInteger("CureStep"));
        pa.setAge(comp.getInteger("Age"));
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        final NBTTagCompound comp = new NBTTagCompound();
        comp.setString("Name", pa.getCondition().getName());
        comp.setInteger("Age", pa.getAge());
        comp.setInteger("CureStep", pa.getCureStep());
        comp.setDouble("Severity", pa.getSeverity());
        ByteBufUtils.writeTag(buf, comp);
    }

    public HealthConditionInstance getAttribute() {
        return pa;
    }

    public static class SPacketAddHealthConditionInstanceHandler
            implements IMessageHandler<SPacketAddHealthConditionInstance, IMessage> {

        @Override
        public IMessage onMessage(final SPacketAddHealthConditionInstance message, final MessageContext ctx) {
            final Minecraft mc = Minecraft.getMinecraft();
            final IProfile profile = mc.player.getCapability(ProfileProvider.PROFILE_CAP, null);
            profile.addHealthCondition(message.getAttribute());
            return null;
        }

    }
}
