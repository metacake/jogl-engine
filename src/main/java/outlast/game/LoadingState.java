package outlast.game;

import io.metacake.core.Bootstrapper;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.TransitionState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.shader.CreateShaderInstruction;
import outlast.engine.output.shader.ShaderProgram;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadingState extends UserState {

    public static final float[] vertices = {
            0.75f, 0.75f, 0.0f, 1.0f,
            0.75f, -0.75f, 0.0f, 1.0f,
            -0.75f, -0.75f, 0.0f, 1.0f,
    };

    private boolean sentInit = false;
    private long time = 0;
    Asset<ShaderProgram> shaderAsset = new Asset<>(new ShaderProgram());
    Asset<Integer> vbo = new Asset<>();
    Asset<Integer> vao = new Asset<>();

    @Override
    public GameState tick() {
        if(time > 3000) {
            return TransitionState.transitionWithTriggers(new MainState(shaderAsset, vbo));
        } else {
            time += Bootstrapper.DEFAULT_LOOP_MILLIS;
            return this;
        }
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        if(!sentInit) {
            sentInit = true;
            return initialLoad();
        } else {
            return RenderingInstructionBundle.EMPTY_BUNDLE;
        }
    }

    RenderingInstructionBundle initialLoad() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        Path vPath = Paths.get("engine", "src", "main", "resources", "vertex.glsl");
        Path fPath = Paths.get("engine", "src", "main", "resources", "fragment.glsl");
        CreateShaderInstruction inst = ShaderProgram.create(shaderAsset).addVertexShader(vPath).addFragmentShader(fPath);
        bundle.add(JOGLDevice.NAME, inst);
        bundle.add(JOGLDevice.NAME, new SetupVBO(vbo, vao, vertices));
        return bundle;
    }
}