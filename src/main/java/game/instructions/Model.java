package game.instructions;

import joglengine.output.shader.ShaderProgram;
import joglengine.util.math.Transformation;

import javax.media.opengl.GL3;

public class Model {

    private Mesh mesh;
    private Transformation transformation;

    public Model(Mesh mesh, Transformation transformation) {
        this.mesh = mesh;
        this.transformation = transformation;
    }

    public void render(GL3 gl, ShaderProgram shader) {
        shader.uniformMat4(gl, "modelToCamera", transformation.getRawMatrix());
        mesh.render(gl);
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }
}