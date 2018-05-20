package me.Jonathon594.Mythria.DataTypes.DeityAbilities;

import me.Jonathon594.Mythria.Enum.Deity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class DASpider extends DeityAbilitySummonMob {
    public DASpider() {
        super("Spider", 10, Deity.LILASIA);
    }

    @Override
    public Entity getMob(World world) {
        return new EntitySpider(world);
    }
}
