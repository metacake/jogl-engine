package joglengine.output.buffer;

import com.jogamp.common.nio.Buffers;
import joglengine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import java.nio.IntBuffer;

public class GenerateVAOInstruction implements JOGLInstruction<GL3> {

    private VertexAttributeObject vao;

    public static GenerateVAOInstruction create() {
        return new GenerateVAOInstruction();
    }

    private GenerateVAOInstruction() {
        this.vao = new VertexAttributeObject();
    }

    public GenerateVAOInstruction withVertexAttribute(VertexAttribute vertexAttribute) {
        vao.addVertexAttribute(vertexAttribute);
        return this;
    }

    public VertexAttributeObject getVao() {
        return vao;
    }

    @Override
    public void render(GL3 gl) {
        generateVAO(gl);
    }

    protected void generateVAO(GL3 gl) {
        IntBuffer vaoHandle = Buffers.newDirectIntBuffer(1);
        gl.glGenVertexArrays(1, vaoHandle);
        int vaop = vaoHandle.get();

        gl.glBindVertexArray(vaop);
        vao.setHandle(vaop);
        gl.glBindVertexArray(0);
    }
}