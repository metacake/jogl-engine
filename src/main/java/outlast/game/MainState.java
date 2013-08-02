package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.JOGLInstruction;
import outlast.engine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;

public class MainState extends UserState {
    private Asset<ShaderProgram> shader;
    private Asset<Mesh> mesh;

    public MainState(Asset<ShaderProgram> shader, Asset<Mesh> mesh) {
        this.shader = shader;
        this.mesh = mesh;
    }

    @Override
    public GameState tick() {
        return this;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(JOGLDevice.NAME, new RenderMesh(mesh.getValue(), shader.getValue()));
        return bundle;
    }
}