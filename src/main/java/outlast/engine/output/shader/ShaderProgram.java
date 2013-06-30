package outlast.engine.output.shader;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.Asset;
import outlast.engine.output.NativeObject;

import javax.media.opengl.GL3;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderProgram extends NativeObject {

    public static CreateShaderInstruction create(Asset<ShaderProgram> asset) {
        return new CreateShaderInstruction(asset);
    }

    public Map<String, Integer> uniforms;
    public Map<String, Integer> attributes;

    public void useProgram(GL3 gl) {
        gl.glUseProgram(getHandle());
    }

    public void disuseProgram(GL3 gl) {
        gl.glUseProgram(0);
    }

    public int getUniformLocation(String name) {
        return uniforms.get(name);
    }

    public int getAttributeLocation(String name) {
        return attributes.get(name);
    }

    void createProgram(GL3 gl, List<Shader> shaders) {
        if(getHandle() < 0) {
            for(Shader shader : shaders) {
                shader.compile(gl);
            }
            int handle = gl.glCreateProgram();
            for(Shader shader : shaders) {
                gl.glAttachShader(handle, shader.getHandle());
            }
            linkPrograms(gl, handle);
            for(Shader shader : shaders) {
                gl.glDetachShader(handle, shader.getHandle());
            }
            this.setHandle(handle);
            uniforms = getUniforms(gl);
            attributes = getAttrbutes(gl);
        }
    }

    void createProgram(GL3 gl, Shader...shaders) {
        createProgram(gl, Arrays.asList(shaders));
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

    private Map<String, Integer> getUniforms(GL3 gl) {
        Map<String, Integer> uniforms = new HashMap<>();
        int handle = getHandle();
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
            uniforms.put(name, gl.glGetUniformLocation(handle, name));
        }
        return uniforms;
    }

    private Map<String, Integer> getAttrbutes(GL3 gl) {
        Map<String, Integer> uniforms = new HashMap<>();
        int handle = getHandle();
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
            uniforms.put(name, gl.glGetAttribLocation(handle, name));
        }
        return uniforms;
    }
}