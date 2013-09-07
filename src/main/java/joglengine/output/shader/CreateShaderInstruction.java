package joglengine.output.shader;

import com.jogamp.common.nio.Buffers;
import joglengine.output.JOGLInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.opengl.GL3;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateShaderInstruction implements JOGLInstruction<GL3> {
    private static final Logger logger = LoggerFactory.getLogger(CreateShaderInstruction.class);

    public static CreateShaderInstruction create(ShaderProgram shaderProgram) {
        return new CreateShaderInstruction(shaderProgram);
    }

    private List<Shader> shaders;
    private ShaderProgram shaderProgram;

    private CreateShaderInstruction(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
        shaders = new ArrayList<>();
    }

    /* Builder Methods */

    public CreateShaderInstruction with(Shader...shaders) {
        this.shaders.addAll(Arrays.asList(shaders));
        return this;
    }

    public CreateShaderInstruction withVertexShader(String source) {
        this.shaders.add(new VertexShader(source));
        return this;
    }

    public CreateShaderInstruction withFragmentShader(String source) {
        this.shaders.add(new FragmentShader(source));
        return this;
    }

    /* Rendering */

    @Override
    public void render(GL3 gl) {
        createProgram(gl, shaders);
    }

    private void createProgram(GL3 gl, List<Shader> shaders) {
        for (Shader shader : shaders) {
            compile(gl, shader);
        }
        int handle = gl.glCreateProgram();
        for (Shader shader : shaders) {
            gl.glAttachShader(handle, shader.getHandle());
        }
        linkPrograms(gl, handle);
        for (Shader shader : shaders) {
            gl.glDetachShader(handle, shader.getHandle());
        }
        shaderProgram.setHandle(handle);
        getUniforms(gl, handle);
        getAttributes(gl, handle);
    }

    private void linkPrograms(GL3 gl, int handle) {
        gl.glLinkProgram(handle);
        if(!isLinked(gl, handle)) {
            printLinkerError(gl, handle);
        }
    }

    private void printLinkerError(GL3 gl, int handle) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetProgramiv(handle, GL3.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetProgramInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        logger.error(new String(bytes));
    }

    private boolean isLinked(GL3 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetProgramiv(handle, GL3.GL_INFO_LOG_LENGTH, status);
        return status.get() == GL3.GL_TRUE;
    }

    private void getUniforms(GL3 gl, int handle) {
        IntBuffer countBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(handle, GL3.GL_ACTIVE_UNIFORMS, countBuffer);
        int limit = countBuffer.get();
        for(int i = 0; i < limit; i++) {
            IntBuffer length = IntBuffer.allocate(1);
            ByteBuffer buffer = ByteBuffer.allocate(100);
            gl.glGetActiveUniformName(handle, i, 100, length, buffer);
            byte[] bytearr = new byte[buffer.remaining()];
            buffer.get(bytearr);
            String name = new String(bytearr).trim();
            shaderProgram.addUniform(name, gl.glGetUniformLocation(handle, name));
        }
    }

    private void getAttributes(GL3 gl, int handle) {
        IntBuffer countBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(handle, GL3.GL_ACTIVE_ATTRIBUTES, countBuffer);
        int limit = countBuffer.get();
        for(int i = 0; i < limit; i++) {
            IntBuffer length = IntBuffer.allocate(1);
            ByteBuffer buffer = ByteBuffer.allocate(100);
            gl.glGetActiveAttrib(handle, i, 100, length, IntBuffer.allocate(1), IntBuffer.allocate(1), buffer);
            byte[] bytearr = new byte[buffer.remaining()];
            buffer.get(bytearr);
            String name = new String(bytearr).trim();
            shaderProgram.addAttribute(name, gl.glGetAttribLocation(handle, name));
        }
    }

    private void compile(GL3 gl, Shader shader) {
            int handle = gl.glCreateShader(shader.getShaderType());
            gl.glShaderSource(handle, 1, new String[] {shader.getSource()}, null, 0);
            gl.glCompileShader(handle);
            if(!isCompiled(gl, handle)) {
                printCompileErrorLog(gl, handle);
            }
        shader.setHandle(handle);
    }

    private void printCompileErrorLog(GL3 gl, int handle) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL3.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetShaderInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        logger.error(new String(bytes));
    }

    private boolean isCompiled(GL3 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL3.GL_COMPILE_STATUS, status);
        return status.get() == GL3.GL_TRUE;
    }
}