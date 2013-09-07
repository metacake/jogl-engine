package game.instructions;

import joglengine.output.JOGLInstruction;
import joglengine.output.buffer.BufferObject;
import joglengine.output.buffer.VertexAttributeObject;

import javax.media.opengl.GL3;

public class MeshContext implements JOGLInstruction<GL3>{

    private BufferObject vertexBuffer, indexBuffer;
    private VertexAttributeObject vertexAttributeObject;

    public void setVertexBuffer(BufferObject vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }

    public void setIndexBuffer(BufferObject indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    public void setVertexAttributeObject(VertexAttributeObject vertexAttributeObject) {
        this.vertexAttributeObject = vertexAttributeObject;
    }

    @Override
    public void render(GL3 gl) {
        vertexAttributeObject.bind(gl);
        vertexBuffer.bind(gl);
        vertexAttributeObject.enable(gl);
        indexBuffer.bind(gl);
    }
}