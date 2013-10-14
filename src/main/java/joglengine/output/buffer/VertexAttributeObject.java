package joglengine.output.buffer;

import com.jogamp.common.nio.Buffers;
import joglengine.output.NativeObject;

import javax.media.opengl.GL3;
import java.util.ArrayList;
import java.util.List;

public class VertexAttributeObject extends NativeObject {

    private List<VertexAttribute> attributes;

    public VertexAttributeObject() {
        this(new ArrayList<VertexAttribute>());
    }

    public VertexAttributeObject(List<VertexAttribute> attributes) {
        this.attributes = attributes;
    }

    public void addVertexAttribute(VertexAttribute vertexAttribute) {
        attributes.add(vertexAttribute);
    }

    public int stride() {
        int result = 0;
        for(VertexAttribute attribute : attributes) {
            result = result + attribute.count;
        }
        return result;
    }

    public void enable(GL3 gl) {
        int stride = stride();
        for(VertexAttribute attribute : attributes) {
            attribute.enableAttribute(gl, stride * Buffers.SIZEOF_FLOAT);
        }
    }

    public void disable(GL3 gl) {
        for(VertexAttribute attribute : attributes) {
            attribute.disableAttribute(gl);
        }
    }

    public void bind(GL3 gl) {
        gl.glBindVertexArray(getHandle());
    }

    public void unbind(GL3 gl) {
        gl.glBindVertexArray(0);
    }

    protected void setHandle(int handle) {
        super.setHandle(handle);
    }
}