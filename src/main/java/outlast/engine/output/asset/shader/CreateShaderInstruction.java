package outlast.engine.output.asset.shader;

import outlast.engine.output.Asset;
import outlast.engine.output.JOGLInstruction;

import javax.media.opengl.GL3;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateShaderInstruction extends JOGLInstruction<GL3> {

    private List<Shader> shaders;
    private Asset<ShaderProgram> shaderAsset;

    CreateShaderInstruction(Asset<ShaderProgram> shaderAsset) {
        this.shaderAsset = shaderAsset;
        shaders = new ArrayList<>();
    }

    /* Creational Methods */

    public CreateShaderInstruction with(Shader...shaders) {
        this.shaders.addAll(Arrays.asList(shaders));
        return this;
    }

    public CreateShaderInstruction addVertexShader(Path path) {
        this.shaders.add(new VertexShader(path));
        return this;
    }

    public CreateShaderInstruction addFragmentShader(Path path) {
        this.shaders.add(new FragmentShader(path));
        return this;
    }

    /* Rendering */

    @Override
    public void render(GL3 gl) {
        shaderAsset.getValue().createProgram(gl, shaders);
    }
}