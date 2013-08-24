package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.shader.ShaderProgram;

import java.util.List;

public class MainState extends UserState {
    private final ScreenClearInstruction clearInstruction = new ScreenClearInstruction();

    private ShaderProgram shader;
    private MeshContext meshContext;
    private List<Mesh> meshes;

    public MainState(ShaderProgram shader, MeshContext meshContext, List<Mesh> meshes) {
        this.shader = shader;
        this.meshContext = meshContext;
        this.meshes = meshes;
    }

    @Override
    public GameState tick() {
        return this;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(JOGLDevice.NAME, clearInstruction);
        bundle.add(JOGLDevice.NAME, new RenderMesh(meshContext, meshes, shader));
        return bundle;
    }
}