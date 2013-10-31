package joglengine.util.math;

public class Vector3f {
    private float x, y, z;

    public Vector3f() {
        this(0, 0, 0);
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public Vector3f add(Vector3f v) {
        return new Vector3f(x + v.x(), y + v.y(), z + v.z());
    }

    public Vector3f subtract(Vector3f v) {
        return new Vector3f(x - v.x(), y - v.y(), z - v.z());
    }

    public Vector3f negate() {
        return new Vector3f(-x, -y, -z);
    }

    public Vector3f multiply(float scale) {
        return new Vector3f(x * scale, y * scale, z * scale);
    }

    public Vector3f normalize() {
        return this.multiply(1.0f / this.magnitude());
    }

    public float magnitude() {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vector3f crossProduct(Vector3f v) {
        float nx = (this.y() * v.z()) - (this.z() * v.y());
        float ny = (this.z() * v.x()) - (this.x() * v.z());
        float nz = (this.x() * v.y()) - (this.y() * v.x());
        return new Vector3f(nx, ny, nz);
    }

    public String toString() {
        return "[" + this.x() + " " + this.y() + " " + this.z() + "]";
    }
}