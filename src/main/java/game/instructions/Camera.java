package game.instructions;

import joglengine.util.math.Matrix4f;
import joglengine.util.math.Vector3f;


public class Camera {

    // (phi, theta, radius) phi and theta in degrees. Note: Theta is on [-90, 90] not [0, 180]
    private Vector3f sphereCamPos = new Vector3f(0, 0, 1);
    private final Vector3f camTarget = new Vector3f(0, 0, 1);

    public Matrix4f cameraMatrix() {
        return calculateViewMatrix(getCameraPosition(camTarget), camTarget, new Vector3f(0, 1, 0));
    }

    private Matrix4f calculateViewMatrix(Vector3f position, Vector3f look, Vector3f up) {
        Vector3f lookDirection = look.subtract(position).normalize();
        Vector3f rightDirection = look.crossProduct(up.normalize()).normalize();
        Vector3f perpUpDirection = rightDirection.crossProduct(lookDirection);

        Matrix4f rotation = Matrix4f.identity();
        fillColumnWithVector(rotation, rightDirection, 0);
        fillColumnWithVector(rotation, perpUpDirection, 1);
        fillColumnWithVector(rotation, lookDirection.negate(), 2);

        Matrix4f translation = Matrix4f.identity();
        fillColumnWithVector(translation, position.negate(), 3);

        return rotation.transpose().multiply(translation);
    }

    private void fillColumnWithVector(Matrix4f mat, Vector3f v, int column) {
        mat.set(column, 0, v.x());
        mat.set(column, 1, v.y());
        mat.set(column, 2, v.z());
    }

    private Vector3f getCameraPosition(Vector3f target) {
        float sinTheta = (float) Math.sin(theta());
        float cosTheta = (float) Math.cos(theta());
        float sinPhi = (float) Math.sin(phi());
        float cosPhi = (float) Math.cos(phi());
        Vector3f dirToCamera = new Vector3f(sinTheta * cosPhi, cosTheta, sinTheta * sinPhi);

        return dirToCamera.multiply(radius()).add(target);
    }

    private float phi() {
        return (float) Math.toRadians(sphereCamPos.x());
    }

    private float theta() {
        return (float) Math.toRadians(sphereCamPos.y() + 90.0f);
    }

    private float radius() {
        return sphereCamPos.z();
    }
}