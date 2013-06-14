package outlast.engine.output.asset.shader;

import javax.media.opengl.GL3;
import java.nio.file.Path;

public class VertexShader extends Shader {

    public VertexShader(Path filePath) {
        super(filePath);
    }

    @Override
    public int getShaderType() {
        return GL3.GL_VERTEX_SHADER;
    }
}