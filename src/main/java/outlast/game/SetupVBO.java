package outlast.game;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import javax.media.opengl.GL3bc;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class SetupVBO extends JOGLInstruction<GL3> {

    Asset<Integer> vboAsset;
    Asset<Integer> vaoAsset;
    float[] vertices;

    SetupVBO(Asset<Integer> vbo, Asset<Integer> vao, float[] vertices) {
        this.vboAsset = vbo;
        this.vaoAsset = vao;
        this.vertices = vertices;
    }

    @Override
    public void render(GL3 gl) {
        // VBO
        IntBuffer vboHandleBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, vboHandleBuffer);
        int vbo = vboHandleBuffer.get();

        FloatBuffer buffer = Buffers.newDirectFloatBuffer(vertices);
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo);
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, buffer.capacity() * Buffers.SIZEOF_FLOAT, buffer, GL3bc.GL_STATIC_DRAW);
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo);

        setRawAsset(vboAsset, vbo);

        // VAO
        IntBuffer vaoHandle = Buffers.newDirectIntBuffer(1);
        gl.glGenVertexArrays(1, vaoHandle);
        int vao = vaoHandle.get();
        gl.glBindVertexArray(vao);
        setRawAsset(vaoAsset, vao);
    }
}