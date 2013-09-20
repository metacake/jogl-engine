package game.instructions;

import joglengine.output.JOGLInstruction;
import joglengine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;
import java.util.List;

public class RenderMesh implements JOGLInstruction<GL3> {
    MeshContext context;
    List<Mesh> meshes;
    ShaderProgram shader;

    public RenderMesh(MeshContext context, List<Mesh> meshes, ShaderProgram shader) {
        this.context = context;
        this.meshes = meshes;
        this.shader = shader;
    }

    @Override
    public void render(GL3 gl) {
        shader.useProgram(gl);

        context.render(gl);
        meshes.forEach(mesh -> mesh.render(gl));

        shader.disuseProgram(gl);
    }
}
