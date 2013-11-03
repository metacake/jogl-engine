package joglengine.util.math;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;

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
    public void immutable() {
        assertThat(zero.add(two), not(sameInstance(zero)));
        assertThat(zero.add(two), not(sameInstance(two)));
        assertThat(zero.negate(), not(sameInstance(zero)));
        assertThat(zero.multiply(ARBITRARY_SCALAR_VALUE), not(sameInstance(zero)));
    }

    @Test
    public void commutativity() {
        assertThat(one.add(two), equalTo(two.add(one)));
    }

    @Test
    public void associativeAddition() {
        assertThat(three.add(one.add(two)), equalTo(two.add(three.add(one))));
    }

    @Test
    public void additiveIdentity() {
        assertThat(three.add(zero), equalTo(three));
        assertThat(zero.add(three), equalTo(three));
    }

    @Test
    public void additiveInverse() {
        assertThat(one.add(one.negate()), equalTo(zero));
        assertThat(one.negate().add(one), equalTo(zero));
    }

    @Test
    public void distributive() {
        Vector3f overVectorSum = one.multiply(ARBITRARY_SCALAR_VALUE).add(two.multiply(ARBITRARY_SCALAR_VALUE));
        assertThat(one.add(two).multiply(ARBITRARY_SCALAR_VALUE), equalTo(overVectorSum));

        Vector3f overScalarSum = two.multiply(ARBITRARY_SCALAR_VALUE).add(two.multiply(ARBITRARY_SCALAR_VALUE));
        int scalarSum = ARBITRARY_SCALAR_VALUE + ARBITRARY_SCALAR_VALUE;
        assertThat(two.multiply(scalarSum), equalTo(overScalarSum));
    }

    @Test
    public void associativeScaling() {
        Vector3f result = three.multiply(ARBITRARY_SCALAR_VALUE * ARBITRARY_SCALAR_VALUE);
        assertThat(three.multiply(ARBITRARY_SCALAR_VALUE).multiply(ARBITRARY_SCALAR_VALUE), equalTo(result));
    }

    @Test
    public void multiplicativeIdentity() {
        assertThat(three.multiply(1), equalTo(three));
    }
}