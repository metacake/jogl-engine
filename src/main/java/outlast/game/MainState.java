package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.asset.shader.ShaderProgram;

import javax.media.opengl.GL3;

public class MainState extends UserState {
    private Asset<ShaderProgram> shader;
    private Asset<Integer> vboAsset;

    public MainState(Asset<ShaderProgram> shader, Asset<Integer> vboAsset) {
        this.shader = shader;
        this.vboAsset = vboAsset;
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

            gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vboAsset.getValue());
            gl.glEnableVertexAttribArray(0);
            gl.glVertexAttribPointer(0, 4, GL3.GL_FLOAT, false, 0, 0);
            gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 3);

            gl.glDisableVertexAttribArray(0);

            shader.getValue().disuseProgram(gl);
        }
    }
}