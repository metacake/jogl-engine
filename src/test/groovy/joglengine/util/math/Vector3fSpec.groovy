package joglengine.util.math

import spock.lang.Specification

import static org.hamcrest.core.IsEqual.equalTo
import static spock.util.matcher.HamcrestSupport.expect

class Vector3fSpec extends Specification {
    static final Vector3f zero = new Vector3f()
    static final EPSILON = 0.0005

    def randomVector() {
        Random rand = new Random()
        Vector3f.of(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())
    }

    def nRandomVectors(int n) {
        (0..<n).collect { randomVector() }
    }

    def "Addition is Commutative"() {
        expect:
        expect vSum, equalTo(uSum)

        where:
        v << nRandomVectors(10)
        u << nRandomVectors(10)
        vSum = v.add u
        uSum = u.add v
    }

    def "Additive Identity Property"() {
        expect:
        expect vPlusZero, equalTo(v)

        where:
        v << nRandomVectors(10)
        vPlusZero = v.add(zero)
    }

    def "Addition is Associative"() {
        expect:
        expect vSum, equalTo(wSum)

        where:
        v << nRandomVectors(10)
        u << nRandomVectors(10)
        w << nRandomVectors(10)

        vSum = v.add(u.add(w))
        wSum = w.add(v.add(u))
    }
}