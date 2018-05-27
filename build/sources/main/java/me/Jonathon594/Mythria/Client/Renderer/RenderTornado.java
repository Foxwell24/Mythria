package me.Jonathon594.Mythria.Client.Renderer;

import me.Jonathon594.Mythria.Entity.Weather.EntityTornado;
import me.Jonathon594.Mythria.Module.TornadoModule;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderGiantZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class RenderTornado<T extends EntityTornado> extends Render<T> {

    public RenderTornado(RenderManager renderManager) {
        super(renderManager);
    }

    int frame = 0;

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        int tier = entity.getTier();
        ArrayList<Vec3d> points = TornadoModule.getTornadoVerticies(tier);
        for(int i = 0; i < 140 + 17 * tier; i++) {
            int offset = points.size() / (32 + (12 * tier)) * i;
            Vec3d v = points.get(MythriaUtil.WrapInt(frame + offset, 0, points.size()-1));
            EnumParticleTypes part = EnumParticleTypes.EXPLOSION_NORMAL;
            if(Math.random() < 0.05) part = EnumParticleTypes.EXPLOSION_LARGE;
            if(Math.random() < 0.01) part = EnumParticleTypes.EXPLOSION_HUGE;
            entity.world.spawnParticle(part, true, v.x + entity.posX, v.y + entity.posY, v.z + entity.posZ, 0, 0, 0);
        }
        frame++;
        if (frame >= points.size()) frame = 0;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}
