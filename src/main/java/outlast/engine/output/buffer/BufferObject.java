package outlast.engine.output.buffer;

import outlast.engine.output.NativeObject;

import javax.media.opengl.GL3;

public class BufferObject extends NativeObject {
    private int target;

    BufferObject(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    public void bind(GL3 gl) {
        gl.glBindBuffer(target, getHandle());
    }

    public void unbind(GL3 gl) {
        gl.glBindBuffer(target, 0);
    }
}