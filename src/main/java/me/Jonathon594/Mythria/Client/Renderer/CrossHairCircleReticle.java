package me.Jonathon594.Mythria.Client.Renderer;

public class CrossHairCircleReticle extends Reticle {

    private final int range;
    private final int area;

    public CrossHairCircleReticle(final int range, final int area, final float r, final float g, final float b,
                                  final float a) {
        super(r, g, b, a);
        this.range = range;
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    public int getRange() {
        return range;
    }

}
