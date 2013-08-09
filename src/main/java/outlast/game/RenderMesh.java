package outlast.game;

import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;
import java.util.List;

public class RenderMesh extends JOGLInstruction<GL3> {
    MeshContext context;
    List<Asset<Mesh>> meshes;
    ShaderProgram shader;

    public RenderMesh(MeshContext context, List<Asset<Mesh>> meshes, ShaderProgram shader) {
        this.context = context;
        this.meshes = meshes;
        this.shader = shader;
    }

    @Override
    public void render(GL3 gl) {
        shader.useProgram(gl);

        context.render(gl);

        for(Asset<Mesh> mesh : meshes) {
            mesh.getValue().render(gl);
        }

        shader.disuseProgram(gl);
    }
}
