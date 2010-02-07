package sprites;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class SpriteVector {
    public double x;
    public double y;

    public SpriteVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof SpriteVector)) return false;
        SpriteVector other = (SpriteVector)that;
        return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(other.x)
            && Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y);
    }
}
