package me.Jonathon594.Mythria.DataTypes.DeityAbilities;

import me.Jonathon594.Mythria.Entity.Weather.EntityTornado;
import me.Jonathon594.Mythria.Enum.Deity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class DATornado extends DeityAbility {
    public DATornado() {
        super("Tornado", 35, Deity.ELIANA);
    }

    @Override
    public boolean execute(EntityPlayer player, EntityPlayer target) {
        EntityTornado tornado = new EntityTornado(player.world);
        tornado.setTier(2);
        player.world.spawnEntity(tornado);
        tornado.rotationYaw = player.rotationYaw;
        return true;
    }
}
