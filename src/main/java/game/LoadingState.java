package game;

import com.jogamp.common.nio.Buffers;
import game.instructions.Mesh;
import game.instructions.MeshBuilder;
import game.instructions.MeshContext;
import game.instructions.Model;
import io.metacake.core.output.InspectingRenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.TransitionState;
import joglengine.output.JOGLDevice;
import joglengine.output.JOGLInstruction;
import joglengine.output.buffer.VertexAttribute;
import joglengine.output.shader.CreateShaderInstruction;
import joglengine.output.shader.ShaderProgram;
import joglengine.state.LoadingPhase;
import joglengine.state.PhaseLoadingState;
import joglengine.util.math.MatrixUtil;
import joglengine.util.math.Transformation;
import joglengine.util.math.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GL3;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LoadingState extends PhaseLoadingState {
    private static final Logger logger = LoggerFactory.getLogger(LoadingState.class);
    public static final float[] CUBE = {
            +1.0f, +1.0f, +1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
            -1.0f, -1.0f, +1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            -1.0f, +1.0f, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            +1.0f, -1.0f, -1.0f, 1.0f, 0.5f, 0.5f, 0.0f, 1.0f,

            -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
            +1.0f, +1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            +1.0f, -1.0f, +1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            -1.0f, +1.0f, +1.0f, 1.0f, 0.5f, 0.5f, 0.0f, 1.0f
    };

    public static final short[] INDICES = {
            0, 1, 2,
            1, 0, 3,
            2, 3, 0,
            3, 2, 1,

            5, 4, 6,
            4, 5, 7,
            7, 6, 4,
            6, 7, 5
    };


    static String getSource(Path filePath) {
        StringBuilder builder = new StringBuilder();
        try {
            for (String line : Files.readAllLines(filePath, Charset.defaultCharset())) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }

    ShaderProgram shaderProgram = new ShaderProgram();
    MeshContext meshContext;
    List<Model> models = new ArrayList<>();
    Transformation transformation = new Transformation();

    public LoadingState() {
        this.addLoadingPhase(phase0());
        this.addLoadingPhase(phase1());
        this.addLoadingPhase(phase2());
        this.addLoadingPhase(phase3());
        transformation.translate(0, 0, -15);
        transformation.getRawMatrix().logMatrix(logger);
    }

    private LoadingPhase phase0() {
        Supplier<InspectingRenderingInstructionBundle> supplier = () -> {
            logger.info("Phase 0");
            InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle();
            Path vPath = Paths.get("src", "main", "resources", "vertex.glsl");
            Path fPath = Paths.get("src", "main", "resources", "fragment.glsl");
            CreateShaderInstruction inst = CreateShaderInstruction.create(shaderProgram);
            inst.withVertexShader(getSource(vPath)).withFragmentShader(getSource(fPath));
            bundle.add(JOGLDevice.NAME, inst);
            bundle.add(JOGLDevice.NAME, (JOGLInstruction<GL3>) (GL3 gl) -> {
                shaderProgram.useProgram(gl);
                shaderProgram.uniformMat4(gl, "cameraToClip", MatrixUtil.perspective(45.0f, 800.0f / 600.0f, 1.0f, 100.0f));
                shaderProgram.disuseProgram(gl);
            });
            return bundle;
        };
        return new LoadingPhase(supplier);
    }

    private LoadingPhase phase1() {
        Supplier<InspectingRenderingInstructionBundle> supplier = () -> {
            logger.info("Phase 1");
            InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle();
            MeshBuilder builder = MeshBuilder.create(
                    new VertexAttribute(shaderProgram.getAttributeLocation("position"), 4, 0),
                    new VertexAttribute(shaderProgram.getAttributeLocation("color"), 4, 4 * Buffers.SIZEOF_FLOAT));
            Mesh mesh = builder.createMesh(CUBE, INDICES);
            meshContext = builder.getMeshContext();
            bundle.add(JOGLDevice.NAME, builder);
            models.add(new Model(mesh, transformation));
            return bundle;
        };
        return new LoadingPhase(supplier);
    }

    private LoadingPhase phase2() {
        Supplier<InspectingRenderingInstructionBundle> supplier = () -> {
            logger.info("Phase 2");
            InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle();
            bundle.add(JOGLDevice.NAME, (JOGLInstruction<GL3>) (GL3 gl) -> {
                gl.glEnable(GL3.GL_CULL_FACE);
                gl.glCullFace(GL3.GL_BACK);
                gl.glFrontFace(GL3.GL_CW);
            });
            return bundle;
        };
        return new LoadingPhase(supplier);
    }

    private LoadingPhase phase3() {
        Supplier<InspectingRenderingInstructionBundle> supplier = () -> {
            InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle();
            bundle.add(JOGLDevice.NAME, (JOGLInstruction<GL3>) (GL3 gl) -> {
                gl.glEnable(GL3.GL_DEPTH_TEST);
                gl.glDepthMask(true);
                gl.glDepthFunc(GL3.GL_LEQUAL);
                gl.glDepthRange(0.0, 1.0);
            });
            return bundle;
        };
        return new LoadingPhase(supplier);
    }

    @Override
    protected GameState nextState() {
        return TransitionState.transitionWithTriggers(new MainState(shaderProgram, meshContext, models));
    }
}