package me.Jonathon594.Mythria.Client.Renderer;

public class ConeReticle extends Reticle {
    public float length;
    public float maxAngle;

    public ConeReticle(final float length, final float maxAngle, final float r, final float g, final float b,
                       final float a) {
        super(r, g, b, a);
        this.length = length;
        this.maxAngle = maxAngle;
    }

}
