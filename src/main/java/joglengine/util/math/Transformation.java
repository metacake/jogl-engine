package joglengine.util.math;

public class Transformation {
    private Matrix4f transformation = Matrix4f.identity();

    public Transformation() {}

    public void translate(float x, float y, float z) {
        transformation.set(3, 0, transformation.get(3, 0) + x);
        transformation.set(3, 1, transformation.get(3, 1) + y);
        transformation.set(3, 2, transformation.get(3, 2) + z);
    }

    public void translate(Vector3f v) {
        this.translate(v.x(), v.y(), v.z());
    }

    // TODO: Deep copy this matrix
    public Matrix4f getRawMatrix() {
        return this.transformation;
    }
}