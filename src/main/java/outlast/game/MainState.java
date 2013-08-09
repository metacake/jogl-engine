package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.shader.ShaderProgram;

public class MainState extends UserState {
    private Asset<ShaderProgram> shader;
    private Asset<MeshContext> meshContextAsset;

    public MainState(Asset<ShaderProgram> shader, Asset<MeshContext> meshContextAsset) {
        this.shader = shader;
        this.meshContextAsset = meshContextAsset;
    }

    @Override
    public GameState tick() {
        return this;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(JOGLDevice.NAME, new RenderMesh(meshContextAsset.getValue(), shader.getValue()));
        return bundle;
    }
}