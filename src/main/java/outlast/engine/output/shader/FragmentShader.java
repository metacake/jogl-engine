package outlast.engine.output.shader;

import javax.media.opengl.GL3;
import java.nio.file.Path;

public class FragmentShader extends Shader {

    public FragmentShader(String source) {
        super(source);
    }

    @Override
    public int getShaderType() {
        return GL3.GL_FRAGMENT_SHADER;
    }
}