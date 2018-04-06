package me.Jonathon594.Mythria.Entity;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityNPCHumanFemale extends EntityNPCPlayer {

    public EntityNPCHumanFemale(final World worldIn) {
        super(worldIn);
    }

    @Override
    public ResourceLocation getNPCTexture() {
        return new ResourceLocation("mythria:textures/entity/npc_human_female.png");
    }
}
