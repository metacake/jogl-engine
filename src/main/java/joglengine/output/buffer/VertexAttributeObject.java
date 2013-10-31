package joglengine.output.buffer;

import com.jogamp.common.nio.Buffers;
import joglengine.output.NativeObject;

import javax.media.opengl.GL3;
import java.util.ArrayList;
import java.util.List;

public class VertexAttributeObject extends NativeObject {

    private List<VertexAttribute> attributes;

    public VertexAttributeObject() {
        this(new ArrayList<>());
    }

    public VertexAttributeObject(List<VertexAttribute> attributes) {
        this.attributes = attributes;
    }

    public void addVertexAttribute(VertexAttribute vertexAttribute) {
        attributes.add(vertexAttribute);
    }

    public int stride() {
        return attributes.stream().reduce(0, ((accumulate, attribute) -> accumulate + attribute.count), Integer::sum);
    }


    public void enable(GL3 gl) {
        int stride = stride();
        // TODO: This is wrong. It will not always be a float, although most often it will be.
        attributes.forEach(attribute -> attribute.enableAttribute(gl, stride * Buffers.SIZEOF_FLOAT));
    }

    public void disable(GL3 gl) {
        attributes.forEach(attribute -> attribute.disableAttribute(gl));
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