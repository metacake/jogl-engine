package game;

import com.jogamp.common.nio.Buffers;
import game.instructions.Mesh;
import game.instructions.MeshBuilder;
import game.instructions.MeshContext;
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
    public static final float[] CUBE = {
            0.25f,  0.25f, -1.25f, 1.0f,   0.0f, 0.0f, 1.0f, 1.0f,
            0.25f, -0.25f, -1.25f, 1.0f,   0.0f, 0.0f, 1.0f, 1.0f,
            -0.25f,  0.25f, -1.25f, 1.0f,  0.0f, 0.0f, 1.0f, 1.0f,

            0.25f, -0.25f, -1.25f, 1.0f,   0.0f, 0.0f, 1.0f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,  0.0f, 0.0f, 1.0f, 1.0f,
            -0.25f,  0.25f, -1.25f, 1.0f,  0.0f, 0.0f, 1.0f, 1.0f,

            0.25f,  0.25f, -2.75f, 1.0f,   0.8f, 0.8f, 0.8f, 1.0f,
            -0.25f,  0.25f, -2.75f, 1.0f,  0.8f, 0.8f, 0.8f, 1.0f,
            0.25f, -0.25f, -2.75f, 1.0f,   0.8f, 0.8f, 0.8f, 1.0f,

            0.25f, -0.25f, -2.75f, 1.0f,   0.8f, 0.8f, 0.8f, 1.0f,
            -0.25f,  0.25f, -2.75f, 1.0f,  0.8f, 0.8f, 0.8f, 1.0f,
            -0.25f, -0.25f, -2.75f, 1.0f,  0.8f, 0.8f, 0.8f, 1.0f,

            -0.25f,  0.25f, -1.25f, 1.0f,  0.0f, 1.0f, 0.0f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,  0.0f, 1.0f, 0.0f, 1.0f,
            -0.25f, -0.25f, -2.75f, 1.0f,  0.0f, 1.0f, 0.0f, 1.0f,

            -0.25f,  0.25f, -1.25f, 1.0f,  0.0f, 1.0f, 0.0f, 1.0f,
            -0.25f, -0.25f, -2.75f, 1.0f,  0.0f, 1.0f, 0.0f, 1.0f,
            -0.25f,  0.25f, -2.75f, 1.0f,  0.0f, 1.0f, 0.0f, 1.0f,

            0.25f,  0.25f, -1.25f, 1.0f,   0.5f, 0.5f, 0.0f, 1.0f,
            0.25f, -0.25f, -2.75f, 1.0f,   0.5f, 0.5f, 0.0f, 1.0f,
            0.25f, -0.25f, -1.25f, 1.0f,   0.5f, 0.5f, 0.0f, 1.0f,

            0.25f,  0.25f, -1.25f, 1.0f,   0.5f, 0.5f, 0.0f, 1.0f,
            0.25f,  0.25f, -2.75f, 1.0f,   0.5f, 0.5f, 0.0f, 1.0f,
            0.25f, -0.25f, -2.75f, 1.0f,   0.5f, 0.5f, 0.0f, 1.0f,

            0.25f,  0.25f, -2.75f, 1.0f,   1.0f, 0.0f, 0.0f, 1.0f,
            0.25f,  0.25f, -1.25f, 1.0f,   1.0f, 0.0f, 0.0f, 1.0f,
            -0.25f,  0.25f, -1.25f, 1.0f,  1.0f, 0.0f, 0.0f, 1.0f,

            0.25f,  0.25f, -2.75f, 1.0f,   1.0f, 0.0f, 0.0f, 1.0f,
            -0.25f,  0.25f, -1.25f, 1.0f,  1.0f, 0.0f, 0.0f, 1.0f,
            -0.25f,  0.25f, -2.75f, 1.0f,  1.0f, 0.0f, 0.0f, 1.0f,

            0.25f, -0.25f, -2.75f, 1.0f,   0.0f, 1.0f, 1.0f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,  0.0f, 1.0f, 1.0f, 1.0f,
            0.25f, -0.25f, -1.25f, 1.0f,   0.0f, 1.0f, 1.0f, 1.0f,

            0.25f, -0.25f, -2.75f, 1.0f,   0.0f, 1.0f, 1.0f, 1.0f,
            -0.25f, -0.25f, -2.75f, 1.0f,  0.0f, 1.0f, 1.0f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,  0.0f, 1.0f, 1.0f, 1.0f,
    };

    public static final short[] INDICES = {
            0, 1, 2,
            3, 4, 5,

            6, 7, 8,
            9, 10, 11,

            12, 13, 14,
            15, 16, 17,

            18, 19, 20,
            21, 22, 23,

            24, 25, 26,
            27, 28, 29,

            30, 31, 32,
            33, 34, 35
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
    List<Mesh> meshes = new ArrayList<>();

    public LoadingState() {
        this.addLoadingPhase(phase0());
        this.addLoadingPhase(phase1());
        this.addLoadingPhase(phase2());
    }

    private LoadingPhase phase0() {
        Supplier<InspectingRenderingInstructionBundle> supplier = () -> {
            InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle();
            Path vPath = Paths.get("src", "main", "resources", "vertex.glsl");
            Path fPath = Paths.get("src", "main", "resources", "fragment.glsl");
            CreateShaderInstruction inst = CreateShaderInstruction.create(shaderProgram);
            inst.withVertexShader(getSource(vPath)).withFragmentShader(getSource(fPath));
            bundle.add(JOGLDevice.NAME, inst);
            bundle.add(JOGLDevice.NAME, (JOGLInstruction<GL3>) (GL3 gl) -> {
                    float zNear = 1.0f;
                    float zFar = 10.0f;
//                    Matrix4f mat = MatrixUtil.perspective(45.0f, 800.0f/600.0f, zNear, zFar);
//                    System.out.println(MatrixUtil.perspective(45.0f, 800.0f/600.0f, zNear, zFar));
                    shaderProgram.useProgram(gl);
                    shaderProgram.uniformMat4(gl, "perspectiveMatrix", MatrixUtil.perspective(45.0f, 800.0f/600.0f, zNear, zFar));
                    shaderProgram.disuseProgram(gl);
                });
            return bundle;
        };
        return new LoadingPhase(supplier);
    }

    private LoadingPhase phase1() {
        Supplier<InspectingRenderingInstructionBundle> supplier = () -> {
            InspectingRenderingInstructionBundle bundle = new InspectingRenderingInstructionBundle();
            MeshBuilder builder = MeshBuilder.create(
                    new VertexAttribute(shaderProgram.getAttributeLocation("position"), 4, 0),
                    new VertexAttribute(shaderProgram.getAttributeLocation("color"), 4, 4 * Buffers.SIZEOF_FLOAT));
            meshes.add(builder.createMesh(CUBE, INDICES));
            meshContext = builder.getMeshContext();
            bundle.add(JOGLDevice.NAME, builder);
            return bundle;
        };
        return new LoadingPhase(supplier);
    }

    private LoadingPhase phase2() {
        Supplier<InspectingRenderingInstructionBundle> supplier = () -> {
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

    @Override
    protected GameState nextState() {
        return TransitionState.transitionWithTriggers(new MainState(shaderProgram, meshContext, meshes));
    }
}