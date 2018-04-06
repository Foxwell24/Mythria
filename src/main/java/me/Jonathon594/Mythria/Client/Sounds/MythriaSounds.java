package me.Jonathon594.Mythria.Client.Sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;

public class MythriaSounds {
    public static MythriaSoundEvent ABILITY_ACTIVATE;
    public static MythriaSoundEvent ABILITY_DEACTIVATE;

    public static void registerSounds(final Register<SoundEvent> event) {
        ABILITY_ACTIVATE = new MythriaSoundEvent(new ResourceLocation("mythria", "ability_activate"));
        event.getRegistry().register(ABILITY_ACTIVATE);
        ABILITY_DEACTIVATE = new MythriaSoundEvent(new ResourceLocation("mythria", "ability_deactivate"));
        event.getRegistry().register(ABILITY_DEACTIVATE);
    }
}
