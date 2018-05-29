package me.Jonathon594.Mythria.Client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybindings {

    public static final KeyBinding KeyBindingOpenMenu = new KeyBinding("Open Menu", 34, "Mythria");
    public static final KeyBinding KeyBindingToggleChat = new KeyBinding("Toggle Chat", 21, "Mythria");
    public static final KeyBinding KeyBindingThrowWeapon = new KeyBinding("Throw Weapon", 19, "Mythria");

    public static void Init() {
        ClientRegistry.registerKeyBinding(KeyBindingOpenMenu);
        ClientRegistry.registerKeyBinding(KeyBindingToggleChat);
        ClientRegistry.registerKeyBinding(KeyBindingThrowWeapon);
    }
}
