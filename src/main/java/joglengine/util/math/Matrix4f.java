package joglengine.util.math;

import java.util.Arrays;

/**
 * A column major 4x4 matrix.
 */
public class Matrix4f {
    private static final int SIZE = 4;
    private float[] values;

    public Matrix4f() {
        values = new float[SIZE * SIZE];
    }

    public float get(int column, int row) {
        return values[column*SIZE + row];
    }

    public void set(int column, int row, float value) {
        values[column*SIZE + row] = value;
    }

    public float[] toArray() {
        return Arrays.copyOf(values, values.length);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < SIZE; i++) {
            builder.append("[");
            for(int j = 0; j < SIZE; j++) {
                builder.append(this.get(i,j)).append(" ");
            }
            builder.append("]\n");
        }
        return builder.toString();
    }
}