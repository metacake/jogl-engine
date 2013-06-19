package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.buffer.BufferObject;
import outlast.engine.output.buffer.VertexAttributeObject;
import outlast.engine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;

public class MainState extends UserState {
    private Asset<ShaderProgram> shader;
    private Asset<BufferObject> vboAsset;
    private Asset<VertexAttributeObject> vaoAsset;

    public MainState(Asset<ShaderProgram> shader, Asset<BufferObject> vboAsset, Asset<VertexAttributeObject> vaoAsset) {
        this.shader = shader;
        this.vboAsset = vboAsset;
        this.vaoAsset = vaoAsset;
    }

    @Override
    public GameState tick() {
        return this;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(JOGLDevice.NAME, new TestInstruction());
        return bundle;
    }

    class TestInstruction extends JOGLInstruction<GL3> {

        @Override
        public void render(GL3 gl) {
            shader.getValue().useProgram(gl);

            vboAsset.getValue().bind(gl);
            vaoAsset.getValue().bind(gl);
            vaoAsset.getValue().enable(gl);
            gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 3);
            vaoAsset.getValue().disable(gl);
            shader.getValue().disuseProgram(gl);
        }
    }
}