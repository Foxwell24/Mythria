package me.Jonathon594.Mythria.Entity;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityNPCHumanMale extends EntityNPCPlayer {

    public EntityNPCHumanMale(final World worldIn) {
        super(worldIn);
    }

    @Override
    public ResourceLocation getNPCTexture() {
        return new ResourceLocation("mythria:textures/entity/npc_human_male.png");
    }

}
