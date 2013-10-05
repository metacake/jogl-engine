package joglengine.util.math;

public class Transformation {
    private Matrix4f trans = Matrix4f.identity();

    public Transformation() {}

    /**
     * Translate this trans to (x,y,z) away from the origin (0,0,0).
     */
    public void translate(float x, float y, float z) {
        addValueToMatrixLocation(3, 0, x);
        addValueToMatrixLocation(3, 1, y);
        addValueToMatrixLocation(3, 2, z);
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

    /**
     * @param axis The axis to rotate about
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

        addValueToMatrixLocation(0, 0, xSquared + ((1 - xSquared) * fCos));
        addValueToMatrixLocation(0, 1, (nAxis.x() * nAxis.y() * fInvCos + (nAxis.z() * fSin)));
        addValueToMatrixLocation(0, 2, nAxis.x() * nAxis.z() * fInvCos + (nAxis.y() * fSin));

        addValueToMatrixLocation(1, 0, nAxis.x() * nAxis.y() * fInvCos - (nAxis.z() * fSin));
        addValueToMatrixLocation(1, 1, ySquared + ((1 - ySquared) * fCos));
        addValueToMatrixLocation(1, 2, nAxis.y() * nAxis.z() * fInvCos + (nAxis.x() * fSin));

        addValueToMatrixLocation(2, 0, nAxis.x() * nAxis.z() * fInvCos + (nAxis.y() * fSin));
        addValueToMatrixLocation(2, 1, nAxis.y() * nAxis.z() * fInvCos - (nAxis.x() * fSin));
        addValueToMatrixLocation(2, 2, zSquared + ((1 - zSquared) * fCos));
    }

    private void multiplyValueToMatrixLocation(int column, int row, float value) {
        trans.set(column, row, value * trans.get(column, row));
    }

    private void addValueToMatrixLocation(int column, int row, float value) {
        trans.set(column, row, value + trans.get(column, row));
    }

    // TODO: Deep copy this matrix
    public Matrix4f getRawMatrix() {
        return this.trans;
    }
}