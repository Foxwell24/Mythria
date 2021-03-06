package me.Jonathon594.Mythria.Client.Model;

import me.Jonathon594.Mythria.Client.Renderer.ModelRendererMythria;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBrickPile extends ModelBase
{
    public ModelRendererMythria[] renderer = new ModelRendererMythria[64];

    public ModelBrickPile()
    {
        for (int n = 0; n < 4; n++){
            this.renderer[n] = new ModelRendererMythria(this,0,0);
            int m = (n+8)/8;
            float x = (n %4)*0.25f;
            float y = (m -1)*0.125f;
            float z = 0;

            if (n%8 >=4) z = .5F;

            renderer[n].cubeList.add(new ModelIngot(renderer[n],renderer[n].textureOffsetX, renderer[n].textureOffsetY));
            renderer[n].offsetY = y;
            if (m %2 == 1) {
                renderer[n].rotateAngleY = 1.56F;
                renderer[n].offsetX = x;
                renderer[n].offsetZ = z+.5F;
            } else {
                renderer[n].offsetX = z;
                renderer[n].offsetZ = x;
            }
        }
    }

    public void renderBricks(int i)
    {
        for (int n = 0; n < i; n++)
        {
            renderer[n].render(0.0625F / 2F);
        }
    }
}
