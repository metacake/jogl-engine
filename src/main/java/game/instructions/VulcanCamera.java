package game.instructions;

import joglengine.util.math.Matrix4f;
import joglengine.util.math.Vector3f;

public class VulcanCamera {

    private Vector3f position, view, right, up;
    private float xRotation, yRotation, zRotation;

    public VulcanCamera() {
        position = new Vector3f();
        view = new Vector3f(0, 0, -1.0f);
        right = new Vector3f(1.0f, 0, 0);
        up = new Vector3f(0, 1.0f, 0);
        xRotation = 0;
        yRotation = 0;
        zRotation = 0;
    }

    public void move(Vector3f v) {
        position = position.add(v);
    }

    public void moveForward(float distance) {
        this.move(view.multiply(distance));
    }

    public void strafeRight(float distance) {
        this.move(right.multiply(distance));
    }

    public void moveUp(float distance) {
        this.move(up.multiply(distance));
    }

    /**
     * @param angle An angle in degrees
     */
    public void rotateX(float angle) {
        xRotation += angle;

        Vector3f u = up.multiply((float) Math.sin(Math.toRadians(angle)));
        view = view.multiply((float) Math.cos(Math.toRadians(angle))).add(u).normalize();

        up = view.crossProduct(right).negate();
    }

    public void rotateY(float angle) {
        yRotation += angle;

        Vector3f u = right.multiply((float) Math.sin(Math.toRadians(angle)));
        view = view.multiply((float) Math.cos(Math.toRadians(angle))).subtract(u).normalize();

        right = view.crossProduct(up);
    }

    public void rotateZ(float angle) {
        zRotation += angle;

        Vector3f u = up.multiply((float) Math.sin(Math.toRadians(angle)));
        right = right.multiply((float) Math.cos(Math.toRadians(angle))).add(u).normalize();

        up = view.crossProduct(right).negate();
    }

    public Matrix4f lookAt() {
        Vector3f f = view.subtract(position).normalize();
        Vector3f uNorm = up.normalize();
        Vector3f s = f.crossProduct(uNorm);
        Vector3f u = s.crossProduct(f);

        Matrix4f rotation = Matrix4f.identity();
        fillColumnWithVector(rotation, s, 0);
        fillColumnWithVector(rotation, u, 1);
        fillColumnWithVector(rotation, f.negate(), 2);

        Matrix4f translation = Matrix4f.identity();
        fillColumnWithVector(translation, position.negate(), 3);

        return rotation.transpose().multiply(translation);
    }

    private void fillColumnWithVector(Matrix4f mat, Vector3f v, int column) {
        mat.set(column, 0, v.x());
        mat.set(column, 1, v.y());
        mat.set(column, 2, v.z());
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getZRotation() {
        return zRotation;
    }

    public float getXRotation() {
        return xRotation;
    }

    public float getYRotation() {
        return yRotation;
    }
}