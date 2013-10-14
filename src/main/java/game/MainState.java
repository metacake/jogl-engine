package game;

import game.instructions.*;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.UserState;
import joglengine.output.JOGLDevice;
import joglengine.output.shader.ShaderProgram;
import joglengine.util.math.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainState extends UserState {
    private static final Logger logger = LoggerFactory.getLogger(MainState.class);
    private final ScreenClearInstruction clearInstruction = new ScreenClearInstruction();

    private int angle = 0;
    private ShaderProgram shader;
    private MeshContext meshContext;
    private List<Model> models;
    private Camera camera;

    public MainState(ShaderProgram shader, MeshContext meshContext, List<Model> models) {
        this.shader = shader;
        this.meshContext = meshContext;
        this.models = models;
        camera = new Camera();
    }

    @Override
    public GameState tick() {
        angle = (angle + 1) % 360;
        models.forEach(model -> {
            model.getTransformation().rotate(new Vector3f(1, 1, 1), angle);
        });
        return this;
    }

    @Override
    public RenderingInstructionBundle renderingInstructions() {
        RenderingInstructionBundle bundle = new RenderingInstructionBundle();
        bundle.add(JOGLDevice.NAME, clearInstruction);
        bundle.add(JOGLDevice.NAME, new RenderMesh(meshContext, models, camera, shader));
        return bundle;
    }
}