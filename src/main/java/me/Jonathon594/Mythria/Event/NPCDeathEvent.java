package me.Jonathon594.Mythria.Event;

import me.Jonathon594.Mythria.Entity.EntityNPCPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent;

public class NPCDeathEvent extends EntityEvent {

    private final EntityNPCPlayer npc;
    private final DamageSource cause;

    public NPCDeathEvent(final EntityNPCPlayer npc, final DamageSource cause) {
        super(npc);
        this.npc = npc;
        this.cause = cause;
    }

    public DamageSource getCause() {
        return cause;
    }

    public EntityNPCPlayer getNpc() {
        return npc;
    }

}
