package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.asset.Shader;

import javax.media.opengl.GL3bc;

public class MainState extends UserState {
    private Asset<Shader> shader;
    private Asset<Integer> vboAsset;

    public MainState(Asset<Shader> shader, Asset<Integer> vboAsset) {
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

    class TestInstruction extends JOGLInstruction<GL3bc> {

        @Override
        public void render(GL3bc gl) {
            gl.glUseProgram(shader.getValue().getHandle());

            gl.glBindBuffer(GL3bc.GL_ARRAY_BUFFER, vboAsset.getValue());
            gl.glEnableVertexAttribArray(0);
            gl.glVertexAttribPointer(0, 4, GL3bc.GL_FLOAT, false, 0, 0);
            gl.glDrawArrays(GL3bc.GL_TRIANGLES, 0, 3);

            gl.glDisableVertexAttribArray(0);

            gl.glUseProgram(0);
        }
    }
}

