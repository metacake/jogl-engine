package joglengine.output.shader;

import joglengine.output.NativeObject;
import joglengine.util.math.Matrix4f;

import javax.media.opengl.GL3;
import java.util.HashMap;
import java.util.Map;

public class ShaderProgram extends NativeObject {
    private Map<String, Integer> uniforms = new HashMap<>();
    private Map<String, Integer> attributes = new HashMap<>();

    public void useProgram(GL3 gl) {
        gl.glUseProgram(getHandle());
    }

    public void disuseProgram(GL3 gl) {
        gl.glUseProgram(0);
    }

    public void uniformf(GL3 gl, String name, float value) {
        gl.glUniform1f(this.getUniformLocation(name), value);
    }

    public void uniformMat4(GL3 gl, String name, Matrix4f mat) {
        gl.glUniformMatrix4fv(this.getUniformLocation(name), 1, false, mat.toArray(), 0);
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