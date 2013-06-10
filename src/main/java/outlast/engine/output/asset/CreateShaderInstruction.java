package outlast.engine.output.asset;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateShaderInstruction extends JOGLInstruction<GL3> {
    private String vertexSource, fragmentSource;
    private Asset<Shader> shaderAsset;

    public CreateShaderInstruction(Asset<Shader> shaderAsset, Path vertexFile, Path fragmentFile) {
        this.shaderAsset = shaderAsset;
        try {
            this.vertexSource = getText(vertexFile);
            this.fragmentSource = getText(fragmentFile);
        } catch(IOException io) {
            throw new RuntimeException(io);
        }
    }

    @Override
    public void render(GL3 gl) {
        int handle = gl.glCreateProgram();
        int vertexHandle = compile(gl, vertexSource, GL3.GL_VERTEX_SHADER);
        int fragmentHandle = compile(gl, fragmentSource, GL3.GL_FRAGMENT_SHADER);

        gl.glAttachShader(handle, vertexHandle);
        gl.glAttachShader(handle, fragmentHandle);

        gl.glLinkProgram(handle);
        if(!linked(gl, handle)) {
            printLinkError(gl, handle);
        }
        gl.glDetachShader(handle, vertexHandle);
        gl.glDeleteShader(vertexHandle);
        gl.glDetachShader(handle, fragmentHandle);
        gl.glDeleteShader(fragmentHandle);

        setRawAsset(shaderAsset, new Shader(handle));
    }

    private String getText(Path filePath) throws IOException {
        StringBuilder builder = new StringBuilder();

        try {
            for(String s : Files.readAllLines(filePath, Charset.defaultCharset())) {
                builder.append(s).append("\n");
            }
            return builder.toString();
        }
        catch (IOException e) {
            throw new RuntimeException("Can't find file: " + filePath.getFileName());
        }
    }

    private void printLinkError(GL3 gl, int handle) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetProgramiv(handle, GL3.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetProgramInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        System.err.println(new String(bytes));
    }

    private boolean linked(GL3 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetProgramiv(handle, GL3.GL_INFO_LOG_LENGTH, status);
        return status.get() == GL3.GL_TRUE;
    }

    // Compile individual Shaders

    private int compile(GL3 gl, String source, int shaderType) {
        int handle = gl.glCreateShader(shaderType);
        gl.glShaderSource(handle, 1, new String[] {source}, null, 0);
        gl.glCompileShader(handle);
        if(!compiled(gl, handle)) {
            printCompilerErrors(gl, handle);
        }
        return handle;
    }

    private boolean compiled(GL3 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL3.GL_COMPILE_STATUS, status);
        return status.get() == GL3.GL_TRUE;
    }

    private void printCompilerErrors(GL3 gl, int handle) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL3.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetShaderInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        System.err.println(new String(bytes));
    }
}