package outlast.game;

import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;

public class RenderMesh extends JOGLInstruction<GL3> {
    MeshContext context;
    ShaderProgram shader;

    public RenderMesh(MeshContext context, ShaderProgram shader) {
        this.context = context;
        this.shader = shader;
    }

    @Override
    public void render(GL3 gl) {
        shader.useProgram(gl);

        context.render(gl);
        gl.glDrawElements(GL3.GL_TRIANGLES, 3, GL3.GL_UNSIGNED_SHORT, 0 /* * Buffers.SIZEOF_SHORT*/);
        // I think that I should be unbinding the previously bound shit.

        shader.disuseProgram(gl);
    }
}
