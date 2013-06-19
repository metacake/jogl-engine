package outlast.game;

import io.metacake.core.Bootstrapper;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.TransitionState;
import io.metacake.core.process.state.UserState;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLDevice;
import outlast.engine.output.buffer.BufferObject;
import outlast.engine.output.buffer.VertexAttributeObject;
import outlast.engine.output.shader.CreateShaderInstruction;
import outlast.engine.output.shader.ShaderProgram;

import javax.media.opengl.GL3;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        List<VertexAttributeObject.VertexAttribute> attrs = new ArrayList<>();
        attrs.add(new VertexAttributeObject.VertexAttribute(0, 4, 0));
        vao = new Asset<>(new VertexAttributeObject(attrs));
    }

    @Override
    public GameState tick() {
        if(time > 3000) {
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
        CreateShaderInstruction inst = ShaderProgram.create(shaderAsset).addVertexShader(vPath).addFragmentShader(fPath);
        bundle.add(JOGLDevice.NAME, inst);
        bundle.add(JOGLDevice.NAME, new SetupVBO(vbo, vao, vertices));
        return bundle;
    }
}