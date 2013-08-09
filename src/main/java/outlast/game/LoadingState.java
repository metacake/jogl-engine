package outlast.game;

import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.TransitionState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.buffer.*;
import outlast.engine.output.shader.CreateShaderInstruction;
import outlast.engine.output.shader.ShaderProgram;
import outlast.engine.state.LoadingPhase;
import outlast.engine.state.PhaseLoadingState;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadingState extends PhaseLoadingState {
    public static final float[] vertices = {
            0.75f, 0.75f, 0.0f, 1.0f,
            0.75f, -0.75f, 0.0f, 1.0f,
            -0.75f, -0.75f, 0.0f, 1.0f
    };

    public static final short[] indices = { 0, 1, 2 };

    Asset<ShaderProgram> shaderAsset = new Asset<>(new ShaderProgram());
    Asset<MeshContext> meshContextAsset;

    public LoadingState() {
        super();
        this.addPhase(phase0());
        this.addPhase(phase1());
    }

    private LoadingPhase phase0() {
        return new LoadingPhase() {
            @Override
            public RenderingInstructionBundle getRenderBundle() {
                RenderingInstructionBundle bundle = new RenderingInstructionBundle();
                Path vPath = Paths.get("engine", "src", "main", "resources", "vertex.glsl");
                Path fPath = Paths.get("engine", "src", "main", "resources", "fragment.glsl");
                CreateShaderInstruction inst = ShaderProgram.create(shaderAsset).withVertexShader(vPath).withFragmentShader(fPath);
                bundle.add(JOGLDevice.NAME, inst);
                return bundle;
            }
        };
    }

    private LoadingPhase phase1() {
        return new LoadingPhase() {
            @Override
            public RenderingInstructionBundle getRenderBundle() {
                RenderingInstructionBundle bundle = new RenderingInstructionBundle();
                MeshBuilder builder = new MeshBuilder();
                builder.withVertexAttribute(new VertexAttribute(shaderAsset.getValue().getAttributeLocation("position"), 4, 0));
                builder.withVertices(vertices).withIndices(indices);
                meshContextAsset = builder.getAsset();
                bundle.add(JOGLDevice.NAME, builder);
                return bundle;
            }
        };
    }

    @Override
    protected GameState nextState() {
        return TransitionState.transitionWithTriggers(new MainState(shaderAsset, meshContextAsset));
    }
}