package me.Jonathon594.Mythria.Event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class WallJumpEvent extends PlayerEvent {

    public WallJumpEvent(final EntityPlayer player) {
        super(player);
    }

}
