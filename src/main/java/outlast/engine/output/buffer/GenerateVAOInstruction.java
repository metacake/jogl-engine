package outlast.engine.output.buffer;

import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;

public class GenerateVAOInstruction extends JOGLInstruction<GL3> {

    Asset<VertexAttributeObject> vaoAsset;

    @Override
    public void render(GL3 gl) {

    }
}