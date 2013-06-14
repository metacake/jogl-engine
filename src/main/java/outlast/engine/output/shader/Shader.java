package outlast.engine.output.shader;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.NativeObject;

import javax.media.opengl.GL3;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Shader extends NativeObject {
    private Path filePath;

    public Shader(Path filePath) {
        this.filePath = filePath;
    }

    public abstract int getShaderType();

    boolean compile(GL3 gl) {
        if(this.getHandle() < 0) {
            int handle = gl.glCreateShader(this.getShaderType());
            gl.glShaderSource(handle, 1, new String[] {getSource()}, null, 0);
            gl.glCompileShader(handle);
            if(!isCompiled(gl, handle)) {
                printCompileErrorLog(gl, handle);
                return false;
            }
            this.setHandle(handle);
        }
        return true;
    }

    private void printCompileErrorLog(GL3 gl, int handle) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL3.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetShaderInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        System.out.println(new String(bytes));
    }

    private boolean isCompiled(GL3 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL3.GL_COMPILE_STATUS, status);
        return status.get() == GL3.GL_TRUE;
    }

    private String getSource() {
        StringBuilder builder = new StringBuilder();
        try {
            for(String line : Files.readAllLines(filePath, Charset.defaultCharset())) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch(IOException io) {
            throw new RuntimeException(io);
        }
    }
}