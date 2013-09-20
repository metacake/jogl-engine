package joglengine.output;

import io.metacake.core.output.RenderingInstruction;

import javax.media.opengl.GL;

public interface JOGLInstruction<T extends GL> extends RenderingInstruction<T> {}