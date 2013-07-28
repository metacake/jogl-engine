package outlast.game;

import io.metacake.core.Bootstrapper;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.TransitionState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.buffer.*;
import outlast.engine.output.shader.CreateShaderInstruction;
import outlast.engine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * NOTE: The best way to do this is actually to load in phases. Making a State that load phases in order would be best.
 */
public class LoadingState extends UserState {

    public static final float[] vertices = {
            0.75f, 0.75f, 0.0f, 1.0f,
            0.75f, -0.75f, 0.0f, 1.0f,
            -0.75f, -0.75f, 0.0f, 1.0f,
    };

    private boolean sentInit = false;
    private long time = 0;
    Asset<ShaderProgram> shaderAsset = new Asset<>(new ShaderProgram());
    Asset<BufferObject> vbo = new Asset<>(new BufferObject(GL3.GL_ARRAY_BUFFER));
    Asset<VertexAttributeObject> vao;
    public LoadingState() {
        List<VertexAttribute> attrs = new ArrayList<>();
        // TODO: I don't actually know that the attribute is at zero. But I made an informed guess based on my knowledge of GLSL and got it right.
        attrs.add(new VertexAttribute(0, 4, 0));
        vao = new Asset<>(new VertexAttributeObject(attrs));
    }

    @Override
    public GameState tick() {
        if(time > 1000) {
            return TransitionState.transitionWithTriggers(new MainState(shaderAsset, vbo, vao));
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
        CreateShaderInstruction inst = ShaderProgram.create(shaderAsset).withVertexShader(vPath).withFragmentShader(fPath);
        bundle.add(JOGLDevice.NAME, inst);
        GenerateBufferInstruction vboInstruction = GenerateBufferInstruction.generateBuffer(vbo);
        vboInstruction.withFloatData(vertices).withRenderingHint(GL3.GL_STATIC_DRAW).withStride(4);
        bundle.add(JOGLDevice.NAME, vboInstruction);
        bundle.add(JOGLDevice.NAME, GenerateVAOInstruction.create(vao));
        return bundle;
    }
}