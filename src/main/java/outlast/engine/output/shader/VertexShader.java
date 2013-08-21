package outlast.engine.output.shader;

import javax.media.opengl.GL3;
import java.nio.file.Path;

public class VertexShader extends Shader {

    public VertexShader(String source) {
        super(source);
    }

    @Override
    public int getShaderType() {
        return GL3.GL_VERTEX_SHADER;
    }
}