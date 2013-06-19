package outlast.engine.output.buffer;

import com.jogamp.common.nio.Buffers;
import outlast.engine.output.NativeObject;

import javax.media.opengl.GL3;
import java.util.List;

public class VertexAttributeObject extends NativeObject {

    public static class VertexAttribute {
        int layout, count, offset;

        public VertexAttribute(int layout, int count, int offset) {
            this.layout = layout;
            this.count = count;
            this.offset = offset;
        }
    }

    private List<VertexAttribute> attributes;

    public VertexAttributeObject(List<VertexAttribute> attributes) {
        this.attributes = attributes;
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
            gl.glEnableVertexAttribArray(attribute.layout);
            gl.glVertexAttribPointer(attribute.layout, attribute.count, GL3.GL_FLOAT, false, stride * Buffers.SIZEOF_FLOAT, attribute.offset);
        }
    }

    public void disable(GL3 gl) {
        for(VertexAttribute attribute : attributes) {
            gl.glDisableVertexAttribArray(attribute.layout);
        }
    }

    public void bind(GL3 gl) {
        gl.glBindVertexArray(getHandle());
    }

    public void unbind(GL3 gl) {
        gl.glBindVertexArray(0);
    }
}