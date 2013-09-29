package joglengine.util.math;

public class Vector3f {
    private int x, y, z;

    public Vector3f() {
        this(0,0,0);
    }

    public Vector3f(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}