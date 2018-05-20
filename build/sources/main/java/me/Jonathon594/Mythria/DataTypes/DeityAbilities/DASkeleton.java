package me.Jonathon594.Mythria.DataTypes.DeityAbilities;

import me.Jonathon594.Mythria.Enum.Deity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class DASkeleton extends DeityAbilitySummonMob {
    public DASkeleton() {
        super("Skeleton", 8, Deity.LILASIA);
    }

    @Override
    public Entity getMob(World world) {
        return new EntitySkeleton(world);
    }
}
