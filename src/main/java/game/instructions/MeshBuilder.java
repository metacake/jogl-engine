package game.instructions;

import joglengine.output.JOGLInstruction;
import joglengine.output.buffer.*;

import javax.media.opengl.GL3;
import java.util.Arrays;

public class MeshBuilder implements JOGLInstruction<GL3> {

    private float[] vertices = new float[0];
    private short[] indices = new short[0];

    private GenerateVAOInstruction vaoInst;
    private MeshContext meshContext = new MeshContext();

    public static MeshBuilder create(VertexAttribute... attributes) {
        return new MeshBuilder(attributes);
    }

    private MeshBuilder(VertexAttribute... attributes) {
        vaoInst = GenerateVAOInstruction.create();
        Arrays.asList(attributes).forEach(attribute -> vaoInst.withVertexAttribute(attribute));
    }

    public Mesh createMesh(float[] verts, short[] inds) {
        int originalLength = indices.length;
        addIndices(inds);
        addVertices(verts);
        return new Mesh(originalLength, inds.length);
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

        int offset = vertices.length / vaoInst.getVao().stride();
        for(int i = originalLength; i < indices.length; i++) {
            indices[i] += offset;
        }
    }

    public MeshContext getMeshContext() {
        return meshContext;
    }

    @Override
    public void render(GL3 gl) {
        GenerateBufferInstruction vboInst = GenerateBufferInstruction.generateBuffer(GL3.GL_ARRAY_BUFFER).withRenderingHint(GL3.GL_STATIC_DRAW);
        vboInst.withFloatData(vertices);
        vboInst.render(gl);
        BufferObject vbo = vboInst.getBufferObject();
        meshContext.setVertexBuffer(vbo);

        GenerateBufferInstruction iboInst = GenerateBufferInstruction.generateBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER).withRenderingHint(GL3.GL_STATIC_DRAW);
        iboInst.withShortData(indices);
        iboInst.render(gl);
        BufferObject ibo = iboInst.getBufferObject();
        meshContext.setIndexBuffer(ibo);

        vaoInst.render(gl);
        VertexAttributeObject vao = vaoInst.getVao();
        meshContext.setVertexAttributeObject(vao);
    }
}