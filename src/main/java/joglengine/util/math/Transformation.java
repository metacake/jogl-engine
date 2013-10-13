package joglengine.util.math;

public class Transformation {
    private Matrix4f trans = Matrix4f.identity();

    /**
     * Translate this trans to (x,y,z) away from the origin (0,0,0).
     */
    public void translate(float x, float y, float z) {
        trans.set(3, 0, x);
        trans.set(3, 1, y);
        trans.set(3, 2, z);
    }

    public void translate(Vector3f v) {
        this.translate(v.x(), v.y(), v.z());
    }

    /**
     * Scale this trans by the given x, y, and z directions.
     */
    public void scale(float x, float y, float z) {
        multiplyValueToMatrixLocation(0, 0, x);
        multiplyValueToMatrixLocation(1, 1, y);
        multiplyValueToMatrixLocation(2, 2, z);
    }

    public void scale(Vector3f v) {
        this.scale(v.x(), v.y(), v.z());
    }

    public void rotateX(float angle) {
        float theta = (float) Math.toRadians(angle);
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        trans.set(1, 1, cosTheta);
        trans.set(2, 1, -sinTheta);
        trans.set(1, 2, sinTheta);
        trans.set(2, 2, cosTheta);
    }

    public void rotateY(float angle) {
        float theta = (float) Math.toRadians(angle);
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        trans.set(0, 0, cosTheta);
        trans.set(2, 0, sinTheta);
        trans.set(0, 2, -sinTheta);
        trans.set(2, 2, cosTheta);
    }

    public void rotateZ(float angle) {
        float theta = (float) Math.toRadians(angle);
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        trans.set(0, 0, cosTheta);
        trans.set(1, 0, -sinTheta);
        trans.set(0, 1, sinTheta);
        trans.set(1, 1, cosTheta);
    }

    /**
     * @param axis  The axis to rotate about
     * @param angle The angle of rotation in degrees
     */
    public void rotate(Vector3f axis, float angle) {
        Vector3f nAxis = axis.normalize();
        float rAngle = (float) Math.toRadians(angle);
        float fCos = (float) Math.cos(rAngle);
        float fInvCos = 1 - fCos;
        float fSin = (float) Math.sin(rAngle);
        float xSquared = nAxis.x() * nAxis.x();
        float ySquared = nAxis.y() * nAxis.y();
        float zSquared = nAxis.z() * nAxis.z();

        trans.set(0, 0, xSquared + ((1 - xSquared) * fCos));
        trans.set(0, 1, (nAxis.x() * nAxis.y() * fInvCos + (nAxis.z() * fSin)));
        trans.set(0, 2, nAxis.x() * nAxis.z() * fInvCos + (nAxis.y() * fSin));

        trans.set(1, 0, nAxis.x() * nAxis.y() * fInvCos - (nAxis.z() * fSin));
        trans.set(1, 1, ySquared + ((1 - ySquared) * fCos));
        trans.set(1, 2, nAxis.y() * nAxis.z() * fInvCos + (nAxis.x() * fSin));

        trans.set(2, 0, nAxis.x() * nAxis.z() * fInvCos + (nAxis.y() * fSin));
        trans.set(2, 1, nAxis.y() * nAxis.z() * fInvCos - (nAxis.x() * fSin));
        trans.set(2, 2, zSquared + ((1 - zSquared) * fCos));
    }

    private void multiplyValueToMatrixLocation(int column, int row, float value) {
        trans.set(column, row, value * trans.get(column, row));
    }

    private void addValueToMatrixLocation(int column, int row, float value) {
        trans.set(column, row, value + trans.get(column, row));
    }

    public void reset() {
        trans = Matrix4f.identity();
    }

    // TODO: Deep copy this matrix
    public Matrix4f getRawMatrix() {
        return this.trans;
    }
}