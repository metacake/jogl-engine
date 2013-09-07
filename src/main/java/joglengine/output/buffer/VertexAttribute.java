package joglengine.output.buffer;

import javax.media.opengl.GL3;

public class VertexAttribute {
    int layout, count, offset, type;
    boolean normalized;

    public VertexAttribute(int layout, int count, int offset) {
        this.layout = layout;
        this.count = count;
        this.offset = offset;
        this.normalized = false;
        this.type = GL3.GL_FLOAT;
    }

    public VertexAttribute(int layout, int count, int offset, int type, boolean normalized) {
        this.layout = layout;
        this.count = count;
        this.offset = offset;
        this.type = type;
        this.normalized = normalized;
    }

    /**
     * Enable this attribute.
     * @param gl The GL context
     * @param adjustedStride The number of Stride elements * the size of each value.
     */
    void enableAttribute(GL3 gl, int adjustedStride) {
        gl.glEnableVertexAttribArray(layout);
        gl.glVertexAttribPointer(layout, count, type, normalized, adjustedStride, offset);
    }

    void disableAttribute(GL3 gl) {
        gl.glDisableVertexAttribArray(layout);
    }
}