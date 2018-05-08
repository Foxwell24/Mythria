package me.Jonathon594.Mythria.Packets;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Managers.SoundManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DPacketSoundEvent implements IMessage {

    SoundEvent soundEvent;
    float pitch;
    float volume;

    public DPacketSoundEvent() {
        // TODO Auto-generated constructor stub
    }

    public DPacketSoundEvent(final SoundEvent soundEvent, final float pitch, final float volume) {
        super();
        this.soundEvent = soundEvent;
        this.pitch = pitch;
        this.volume = volume;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        soundEvent = SoundEvent.REGISTRY.getObject(new ResourceLocation(ByteBufUtils.readUTF8String(buf)));
        pitch = buf.readFloat();
        volume = buf.readFloat();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, soundEvent.getSoundName().toString());
        buf.writeFloat(pitch);
        buf.writeFloat(volume);
    }

    public float getPitch() {
        return pitch;
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public float getVolume() {
        return volume;
    }

    public static class DPacketSoundEventHandler implements IMessageHandler<DPacketSoundEvent, IMessage> {

        @Override
        public IMessage onMessage(final DPacketSoundEvent message, final MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                SoundManager.playForAllNearby(ctx.getServerHandler().player, message.getSoundEvent());
            });
            return null;
        }

    }
}
