package outlast.game;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.buffer.BufferObject;
import outlast.engine.output.buffer.GenerateBufferInstruction;
import outlast.engine.output.buffer.VertexAttributeObject;

import javax.media.opengl.GL3;
import java.nio.IntBuffer;

public class SetupVBO extends JOGLInstruction<GL3> {

    Asset<BufferObject> vboAsset;
    Asset<VertexAttributeObject> vaoAsset;
    float[] vertices;

    SetupVBO(Asset<BufferObject> vbo, Asset<VertexAttributeObject> vao, float[] vertices) {
        this.vboAsset = vbo;
        this.vaoAsset = vao;
        this.vertices = vertices;
    }

    @Override
    public void render(GL3 gl) {
        GenerateBufferInstruction bufferInstruction = GenerateBufferInstruction.generateBuffer(vboAsset);
        bufferInstruction.withFloatData(vertices).withRenderingHint(GL3.GL_STATIC_DRAW).withStride(4);
        bufferInstruction.render(gl);

        // VAO
        IntBuffer vaoHandle = Buffers.newDirectIntBuffer(1);
        gl.glGenVertexArrays(1, vaoHandle);
        int vao = vaoHandle.get();
        gl.glBindVertexArray(vao);
        vaoAsset.getValue().setHandle(vao);
    }
}