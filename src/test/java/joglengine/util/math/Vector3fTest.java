package joglengine.util.math;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class Vector3fTest {

    private Vector3f zero, one, two, three;
    private final int ARBITRARY_SCALAR_VALUE = 3;
    private final double EPSILON = 0.0005;

    @Before
    public void setup() {
        zero = new Vector3f();
        one = new Vector3f(1, 1, 1);
        two = new Vector3f(2, 3, 0);
        three = new Vector3f(0, 1, 4);
    }

    @Test
    public void associativeScaling() {
        Vector3f result = three.multiply(ARBITRARY_SCALAR_VALUE * ARBITRARY_SCALAR_VALUE);
        assertThat(three.multiply(ARBITRARY_SCALAR_VALUE).multiply(ARBITRARY_SCALAR_VALUE), equalTo(result));
    }
}