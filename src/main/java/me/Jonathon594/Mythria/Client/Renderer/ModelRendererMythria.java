package me.Jonathon594.Mythria.Client.Renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;

public class ModelRendererMythria extends ModelRenderer {
    public ModelBase modelBase;
    public int textureOffsetX;
    public int textureOffsetY;

    public ModelRendererMythria(ModelBase model) {
        super(model);
        modelBase = model;
    }

    public ModelRendererMythria(ModelBase model, int texOffsetX, int texOffsetY)
    {
        this(model);
        this.setTextureOffset(texOffsetX, texOffsetY);
    }

    @Override
    public ModelRenderer setTextureOffset(int texOffsetX, int texOffsetY)
    {
        this.textureOffsetX = texOffsetX;
        this.textureOffsetY = texOffsetY;
        return this;
    }

    /**
     * Creates a textured box. Args: originX, originY, originZ, width, height, depth, scaleFactor.
     */
    @Override
    public void addBox(float par1, float par2, float par3, int par4, int par5, int par6, float par7)
    {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, par7));
    }

    /**
     * Creates a textured box. Args: originX, originY, originZ, width, height, depth, scaleFactor.
     */
    public void addBox(ModelBox box)
    {
        this.cubeList.add(box);
    }

}
