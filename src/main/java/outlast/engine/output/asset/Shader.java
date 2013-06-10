package outlast.engine.output.asset;

public class Shader {
    private int handle;

    Shader(int handle) {
        this.handle = handle;
    }

    public int getHandle() {
        return handle;
    }
}