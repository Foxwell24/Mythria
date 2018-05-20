package me.Jonathon594.Mythria.DataTypes.DeityAbilities;

import me.Jonathon594.Mythria.Enum.Deity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class DeityAbilitySummonMob extends DeityAbility{

    protected DeityAbilitySummonMob(String name, int cost, Deity owner) {
        super(name, cost, owner);
    }

    public abstract Entity getMob(World world);

    @Override
    public boolean execute(EntityPlayer player, EntityPlayer target) {
        Entity e = getMob(player.world);
        e.setPosition(player.posX, player.posY, player.posZ);
        player.world.spawnEntity(e);
        return true;
    }
}
