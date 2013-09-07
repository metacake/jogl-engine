package game;

import com.jogamp.common.nio.Buffers;
import game.instructions.Mesh;
import game.instructions.MeshBuilder;
import game.instructions.MeshContext;
import io.metacake.core.output.RenderingInstructionBundle;
import io.metacake.core.process.state.GameState;
import io.metacake.core.process.state.TransitionState;
import joglengine.output.JOGLDevice;
import joglengine.output.JOGLInstruction;
import joglengine.output.buffer.VertexAttribute;
import joglengine.output.shader.CreateShaderInstruction;
import joglengine.output.shader.ShaderProgram;
import joglengine.state.LoadingPhase;
import joglengine.state.PhaseLoadingState;
import joglengine.util.math.Matrix4f;

import javax.media.opengl.GL3;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadingState extends PhaseLoadingState {
    public static final float[] CUBE = {
             0.25f, -0.25f,  1.25f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
            -0.25f, -0.25f,  1.25f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
            -0.25f, -0.25f, -1.25f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
             0.25f, -0.25f, -1.25f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
             0.25f,  0.25f,  1.25f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
            -0.25f,  0.25f,  1.25f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
            -0.25f,  0.25f, -1.25f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f,
             0.25f,  0.25f, -1.25f, 1.0f,  1.0f, 1.0f, 1.0f, 1.0f
    };

    public static final short[] INDICES = {
            0, 1, 2,
            4, 7, 6,
            0, 4, 5,

            1, 5, 6,
            2, 6, 7,
            4, 0, 3,

            3, 0, 2,
            5, 4, 6,
            1, 0, 5,

            2, 1, 6,
            3, 2, 7,
            7, 4, 3
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
        super();
        this.addPhase(phase0());
        this.addPhase(phase1());
    }

    private LoadingPhase phase0() {
        return new LoadingPhase() {
            @Override
            public RenderingInstructionBundle getRenderBundle() {
                RenderingInstructionBundle bundle = new RenderingInstructionBundle();
                Path vPath = Paths.get("src", "main", "resources", "vertex.glsl");
                Path fPath = Paths.get("src", "main", "resources", "fragment.glsl");
                CreateShaderInstruction inst = CreateShaderInstruction.create(shaderProgram);
                inst.withVertexShader(getSource(vPath)).withFragmentShader(getSource(fPath));
                bundle.add(JOGLDevice.NAME, inst);
                bundle.add(JOGLDevice.NAME, new JOGLInstruction<GL3>() {
                    @Override
                    public void render(GL3 gl) {
                        float frustumScale = 1.0f;
                        float zNear = 1.0f;
                        float zFar = 3.0f;
                        Matrix4f perspectiveMatrix = new Matrix4f();
                        perspectiveMatrix.set(0, 0, frustumScale);
                        perspectiveMatrix.set(1, 1, frustumScale);
                        perspectiveMatrix.set(2, 2, (zFar + zNear) / (zNear - zFar));
                        perspectiveMatrix.set(2, 3, (2 * zFar * zNear) / (zNear - zFar));
                        perspectiveMatrix.set(3, 2, -1.0f);
                        System.out.println(perspectiveMatrix);
                        shaderProgram.useProgram(gl);
                        shaderProgram.uniformMat4(gl, "perspectiveMatrix", perspectiveMatrix);
                        shaderProgram.disuseProgram(gl);
                    }
                });
                return bundle;
            }
        };
    }

    private LoadingPhase phase1() {
        return new LoadingPhase() {
            @Override
            public RenderingInstructionBundle getRenderBundle() {
                RenderingInstructionBundle bundle = new RenderingInstructionBundle();
                MeshBuilder builder = MeshBuilder.create(
                        new VertexAttribute(shaderProgram.getAttributeLocation("position"), 4, 0),
                        new VertexAttribute(shaderProgram.getAttributeLocation("color"), 4, 4 * Buffers.SIZEOF_FLOAT));
                meshes.add(builder.createMesh(CUBE, INDICES));
                meshContext = builder.getMeshContext();
                bundle.add(JOGLDevice.NAME, builder);
                return bundle;
            }
        };
    }

    @Override
    protected GameState nextState() {
        return TransitionState.transitionWithTriggers(new MainState(shaderProgram, meshContext, meshes));
    }
}