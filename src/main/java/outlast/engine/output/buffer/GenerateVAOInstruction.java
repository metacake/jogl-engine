package outlast.engine.output.buffer;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import java.nio.IntBuffer;

public class GenerateVAOInstruction extends JOGLInstruction<GL3> {

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

    public Asset<VertexAttributeObject> getAsset() {
        return new Asset<>(vao);
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