package outlast.engine.output.shader;

import javax.media.opengl.GL3;
import java.nio.file.Path;

public class FragmentShader extends Shader {

    public FragmentShader(Path filePath) {
        super(filePath);
    }

    @Override
    public int getShaderType() {
        return GL3.GL_FRAGMENT_SHADER;
    }
}