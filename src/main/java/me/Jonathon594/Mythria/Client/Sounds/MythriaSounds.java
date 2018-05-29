package me.Jonathon594.Mythria.Client.Sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;

public class MythriaSounds {
    public static MythriaSoundEvent SAWHORSE_SAW;

    public static void registerSounds(final Register<SoundEvent> event) {
        SAWHORSE_SAW = new MythriaSoundEvent(new ResourceLocation("mythria", "sawhorse_saw"));
        event.getRegistry().register(SAWHORSE_SAW);
    }
}
