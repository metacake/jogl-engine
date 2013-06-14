package outlast.engine.output;

public class NativeObject {
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
}