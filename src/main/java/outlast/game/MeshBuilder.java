package outlast.game;

import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.buffer.*;

import javax.media.opengl.GL3;
import java.util.Arrays;

public class MeshBuilder extends JOGLInstruction<GL3> {

    private float[] vertices;
    private short[] indices;

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
        vertices = Arrays.copyOf(verts, verts.length);
        indices = Arrays.copyOf(inds, inds.length);
        return new Asset<>(new Mesh(0, inds.length));
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