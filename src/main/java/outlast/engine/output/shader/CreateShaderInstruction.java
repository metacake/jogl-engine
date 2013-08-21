package outlast.engine.output.shader;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.util.*;

public class CreateShaderInstruction extends JOGLInstruction<GL3> {

    private List<Shader> shaders;
    private Asset<ShaderProgram> shaderAsset;

    CreateShaderInstruction(Asset<ShaderProgram> shaderAsset) {
        this.shaderAsset = shaderAsset;
        shaders = new ArrayList<>();
    }

    /* Builder Methods */

    public CreateShaderInstruction with(Shader...shaders) {
        this.shaders.addAll(Arrays.asList(shaders));
        return this;
    }

    public CreateShaderInstruction withVertexShader(Path path) {
        this.shaders.add(new VertexShader(path));
        return this;
    }

    public CreateShaderInstruction withFragmentShader(Path path) {
        this.shaders.add(new FragmentShader(path));
        return this;
    }

    /* Rendering */

    @Override
    public void render(GL3 gl) {
        createProgram(gl, shaders);
    }

    void createProgram(GL3 gl, List<Shader> shaders) {
        for (Shader shader : shaders) {
            shader.compile(gl);
        }
        int handle = gl.glCreateProgram();
        for (Shader shader : shaders) {
            gl.glAttachShader(handle, shader.getHandle());
        }
        linkPrograms(gl, handle);
        for (Shader shader : shaders) {
            gl.glDetachShader(handle, shader.getHandle());
        }
        shaderAsset.getValue().setHandle(handle);
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
        System.out.println(new String(bytes));
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
            shaderAsset.getValue().addUniform(name, gl.glGetUniformLocation(handle, name));
        }
    }

    private void getAttributes(GL3 gl, int handle) {
        IntBuffer countBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(handle, GL3.GL_ACTIVE_ATTRIBUTES, countBuffer);
        int limit = countBuffer.get();
        for(int i = 0; i < limit; i++) {
            IntBuffer length = IntBuffer.allocate(1);
            IntBuffer size = IntBuffer.allocate(1);
            ByteBuffer buffer = ByteBuffer.allocate(100);
            gl.glGetActiveAttrib(handle, i, 100, length, IntBuffer.allocate(1), IntBuffer.allocate(1), buffer);
            byte[] bytearr = new byte[buffer.remaining()];
            buffer.get(bytearr);
            String name = new String(bytearr).trim();
            shaderAsset.getValue().addAttribute(name, gl.glGetAttribLocation(handle, name));
        }
    }
}