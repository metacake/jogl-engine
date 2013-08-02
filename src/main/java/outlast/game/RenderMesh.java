package outlast.game;

import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;

public class RenderMesh extends JOGLInstruction<GL3>{
    Mesh mesh;
    ShaderProgram shader;

    public RenderMesh(Mesh mesh, ShaderProgram shader) {
        this.mesh = mesh;
        this.shader = shader;
    }

    @Override
    public void render(GL3 gl) {
        shader.useProgram(gl);

        mesh.vao.bind(gl);
        mesh.vbo.bind(gl);
        mesh.vao.enable(gl);
        mesh.ibo.bind(gl);

        gl.glDrawElements(GL3.GL_TRIANGLES, mesh.indiceCount, GL3.GL_UNSIGNED_SHORT, 0 /* * Buffers.SIZEOF_SHORT*/);

        // I think that I should be unbinding the previously bound shit.

        shader.disuseProgram(gl);
    }
}
