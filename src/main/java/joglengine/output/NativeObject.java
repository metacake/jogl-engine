package joglengine.output;

public abstract class NativeObject {
    private int handle;

    public NativeObject() {
        this.handle = -1;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    public boolean isBound() {
        return handle >= 0;
    }

    // TODO: This will change to be abstract soon
    public void dispose() {}
}