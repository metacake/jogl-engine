package outlast.engine.output.shader;

import outlast.engine.output.NativeObject;

public abstract class Shader extends NativeObject {
    private String source;

    public Shader(String source) {
        this.source = source;
    }

    public final String getSource() {
        return source;
    }

    public abstract int getShaderType();
}