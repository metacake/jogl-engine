package outlast.engine.output.buffer;

import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;

public class GenerateVAOInstruction extends JOGLInstruction<GL3> {

    private Asset<VertexAttributeObject> vaoAsset;

    public static GenerateVAOInstruction create(Asset<VertexAttributeObject> vaoAsset) {
        return new GenerateVAOInstruction(vaoAsset);
    }

    private GenerateVAOInstruction(Asset<VertexAttributeObject> vaoAsset) {
        this.vaoAsset = vaoAsset;
    }

    @Override
    public void render(GL3 gl) {
        vaoAsset.getValue().generateVAO(gl);
    }
}