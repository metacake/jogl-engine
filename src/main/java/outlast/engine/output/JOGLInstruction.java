package outlast.engine.output;

import io.metacake.core.output.RenderingInstruction;

import javax.media.opengl.GL;

public abstract class JOGLInstruction<T extends GL> implements RenderingInstruction {
    public abstract void render(T gl);

    public <T> T getRawAsset(Asset<T> asset) {
        return asset.value;
    }

    public <T> void setRawAsset(Asset<T> asset, T value) {
        asset.setValue(value);
    }
}