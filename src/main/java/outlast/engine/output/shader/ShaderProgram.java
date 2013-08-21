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

    private Map<String, Integer> uniforms = new HashMap<>();
    private Map<String, Integer> attributes = new HashMap<>();

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

    void addAttribute(String name, Integer location) {
        attributes.put(name, location);
    }

    void addUniform(String name, Integer location) {
        uniforms.put(name, location);
    }
}