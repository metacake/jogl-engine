package outlast.game;

import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.buffer.*;

import javax.media.opengl.GL3;

public class MeshBuilder extends JOGLInstruction<GL3> {

    GenerateBufferInstruction vboInst = GenerateBufferInstruction.generateBuffer(GL3.GL_ARRAY_BUFFER).withRenderingHint(GL3.GL_STATIC_DRAW);
    GenerateBufferInstruction iboInst = GenerateBufferInstruction.generateBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER).withRenderingHint(GL3.GL_STATIC_DRAW);
    GenerateVAOInstruction vaoInst = GenerateVAOInstruction.create();
    Asset<MeshContext> meshContextAsset = new Asset<>(new MeshContext());

    public MeshBuilder withVertices(float[] verts) {
        vboInst.withFloatData(verts);
        return this;
    }

    public MeshBuilder withIndices(short[] indices) {
        iboInst.withShortData(indices);
        return this;
    }

    public MeshBuilder withVertexAttribute(VertexAttribute attribute) {
        vaoInst.withVertexAttribute(attribute);
        return this;
    }

    public Asset<MeshContext> getAsset() {
        return meshContextAsset;
    }

    @Override
    public void render(GL3 gl) {
        MeshContext meshContext = meshContextAsset.getValue();
        vboInst.render(gl);
        BufferObject vbo = vboInst.getAsset().getValue();
        meshContext.setVertexBuffer(vbo);

        iboInst.render(gl);
        BufferObject ibo = iboInst.getAsset().getValue();
        meshContext.setIndexBuffer(ibo);

        vaoInst.render(gl);
        VertexAttributeObject vao = vaoInst.getAsset().getValue();
        meshContext.setVertexAttributeObject(vao);
    }
}