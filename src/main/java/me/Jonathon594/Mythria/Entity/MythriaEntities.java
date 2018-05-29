package me.Jonathon594.Mythria.Entity;

import me.Jonathon594.Mythria.Client.Renderer.*;
import me.Jonathon594.Mythria.Entity.Magic.EntityKillingCurse;
import me.Jonathon594.Mythria.Entity.Magic.EntitySingularitySpell;
import me.Jonathon594.Mythria.Entity.Weather.EntityEarthQuake;
import me.Jonathon594.Mythria.Entity.Weather.EntityTornado;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MythriaEntities {
    public static void Initialize() {
        int id = 0;
        EntityRegistry.registerModEntity(new ResourceLocation("mythria:throwingweapon"), EntityThrowingWeapon.class,
                "ThrowingWeapon", id, Mythria.instance, 64, 10, true);

        EntityRegistry.registerModEntity(new ResourceLocation("mythria:npcplayerhumanmale"), EntityNPCHumanMale.class,
                "NPCPlayerHumanMale", id++, Mythria.instance, 80, 3, false);
        EntityRegistry.registerModEntity(new ResourceLocation("mythria:npcplayerhumanfemale"),
                EntityNPCHumanFemale.class, "NPCPlayerHumanFemale", id++, Mythria.instance, 80, 3, false);

        EntityRegistry.registerModEntity(new ResourceLocation("mythria:blackbear"),
                EntityBlackBear.class, "BlackBear", id++, Mythria.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("mythria:brownbear"),
                EntityBrownBear.class, "BrownBear", id++, Mythria.instance, 80, 3, true);

        EntityRegistry.registerModEntity(new ResourceLocation("mythria:tornado"),
                EntityTornado.class, "EntityTornado", id++, Mythria.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("mythria:earthquake"),
                EntityEarthQuake.class, "EntityEarthQuake", id++, Mythria.instance, 80, 3, true);

        EntityRegistry.registerModEntity(new ResourceLocation("mythria:killingcurse"),
                EntityKillingCurse.class, "KillingCurse", id++, Mythria.instance, 64, 10, true);

        EntityRegistry.registerModEntity(new ResourceLocation("mythria:singularityspell"),
                EntityKillingCurse.class, "SingularitySpell", id++, Mythria.instance, 64, 10, true);

        registerSpawns();
    }

    public static void registerSpawns() {
        EntityRegistry.addSpawn(EntityBlackBear.class, 8, 3, 6, EnumCreatureType.CREATURE, Biome.getBiome(4), Biome.getBiome(5));
        EntityRegistry.addSpawn(EntityBrownBear.class, 8, 3, 6, EnumCreatureType.CREATURE, Biome.getBiome(4), Biome.getBiome(5));
    }

    @SideOnly(Side.CLIENT)
    public static void RegisterRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityThrowingWeapon.class,
                manager -> new RenderThrowingWeapon<>(manager, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityNPCPlayer.class, manager -> new RenderNPC<>(manager));

        RenderingRegistry.registerEntityRenderingHandler(EntityBlackBear.class, manager -> new RenderBlackBear<>(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityBrownBear.class, manager -> new RenderBrownBear<>(manager));

        RenderingRegistry.registerEntityRenderingHandler(EntityTornado.class, manager -> new RenderTornado<>(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityEarthQuake.class, manager -> new RenderEmpty<>(manager));

        RenderingRegistry.registerEntityRenderingHandler(EntityKillingCurse.class, manager -> new RenderKillingCurse<>(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntitySingularitySpell.class, manager -> new RenderSingularitySpell<>(manager));
    }
}
