package game.instructions;

import joglengine.output.JOGLInstruction;
import joglengine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;
import java.util.List;

public class RenderMesh implements JOGLInstruction<GL3> {
    MeshContext context;
    List<Model> models;
    ShaderProgram shader;
    Camera camera = new Camera();

    public RenderMesh(MeshContext context, List<Model> models, ShaderProgram shader) {
        this.context = context;
        this.models = models;
        this.shader = shader;
    }

    @Override
    public void render(GL3 gl) {
        shader.useProgram(gl);

        shader.uniformMat4(gl, "worldToCamera", camera.cameraMatrix());

        context.render(gl);
        models.forEach(model -> {
            model.render(gl, shader);
        });

        shader.disuseProgram(gl);
    }
}
