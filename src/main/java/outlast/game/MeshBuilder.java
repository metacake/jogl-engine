package outlast.game;

import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.buffer.*;

import javax.media.opengl.GL3;
import java.util.Arrays;

public class MeshBuilder extends JOGLInstruction<GL3> {

    private float[] vertices = new float[0];
    private short[] indices = new short[0];

    private GenerateVAOInstruction vaoInst;
    private Asset<MeshContext> meshContextAsset = new Asset<>(new MeshContext());

    public static MeshBuilder create(VertexAttribute... attributes) {
        return new MeshBuilder(attributes);
    }

    private MeshBuilder(VertexAttribute... attributes) {
        vaoInst = GenerateVAOInstruction.create();
        for(VertexAttribute attribute : attributes) {
            vaoInst.withVertexAttribute(attribute);
        }
    }

    public Asset<Mesh> createMesh(float[] verts, short[] inds) {
        int originalLength = indices.length;
        addIndices(inds);
        addVertices(verts);
        return new Asset<>(new Mesh(originalLength, inds.length));
    }

    private void addVertices(float[] verts) {
        int originalLength = this.vertices.length;
        vertices = Arrays.copyOf(vertices, originalLength + verts.length);
        System.arraycopy(verts, 0, vertices, originalLength, verts.length);
    }

    private void addIndices(short[] inds) {
        int originalLength = indices.length;
        indices = Arrays.copyOf(indices, originalLength + inds.length);
        System.arraycopy(inds, 0, indices, originalLength, inds.length);

        int offset = vertices.length / vaoInst.getAsset().getValue().stride();
        for(int i = originalLength; i < indices.length; i++) {
            indices[i] += offset;
        }
    }

    public Asset<MeshContext> getAsset() {
        return meshContextAsset;
    }

    @Override
    public void render(GL3 gl) {
        MeshContext meshContext = meshContextAsset.getValue();
        GenerateBufferInstruction vboInst = GenerateBufferInstruction.generateBuffer(GL3.GL_ARRAY_BUFFER).withRenderingHint(GL3.GL_STATIC_DRAW);
        vboInst.withFloatData(vertices);
        vboInst.render(gl);
        BufferObject vbo = vboInst.getAsset().getValue();
        meshContext.setVertexBuffer(vbo);

        GenerateBufferInstruction iboInst = GenerateBufferInstruction.generateBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER).withRenderingHint(GL3.GL_STATIC_DRAW);
        iboInst.withShortData(indices);
        iboInst.render(gl);
        BufferObject ibo = iboInst.getAsset().getValue();
        meshContext.setIndexBuffer(ibo);

        vaoInst.render(gl);
        VertexAttributeObject vao = vaoInst.getAsset().getValue();
        meshContext.setVertexAttributeObject(vao);
    }
}