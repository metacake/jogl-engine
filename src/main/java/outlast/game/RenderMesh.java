package outlast.game;

import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.shader.ShaderProgram;

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

        for(Mesh mesh : meshes) {
            mesh.render(gl);
        }

        shader.disuseProgram(gl);
    }
}
