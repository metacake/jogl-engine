package outlast.game;

import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;

public class RenderMesh extends JOGLInstruction<GL3> {
    MeshContext context;
    Mesh mesh;
    ShaderProgram shader;

    public RenderMesh(MeshContext context, Mesh mesh, ShaderProgram shader) {
        this.context = context;
        this.mesh = mesh;
        this.shader = shader;
    }

    @Override
    public void render(GL3 gl) {
        shader.useProgram(gl);

        context.render(gl);
        mesh.render(gl);
        // I think that I should be unbinding the previously bound shit.

        shader.disuseProgram(gl);
    }
}
