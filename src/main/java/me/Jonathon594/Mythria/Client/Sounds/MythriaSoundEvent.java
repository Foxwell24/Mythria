package me.Jonathon594.Mythria.Client.Sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class MythriaSoundEvent extends SoundEvent {

    public MythriaSoundEvent(final ResourceLocation soundNameIn) {
        super(soundNameIn);
        this.setRegistryName(getSoundName());
    }
}
