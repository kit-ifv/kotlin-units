package unitsOld

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class DistanceDepreTest : GenericUnitTest<DistanceUnitDepre, DistanceDepre>(
    DistanceUnitDepre.entries.toTypedArray(),
    Int::toDistance,
    Long::toDistance,
    Double::toDistance
) {

    @Test
    fun compareTo() {

        val d1 = DistanceDepre.ofMeters(1)
        val d2 = DistanceDepre.ofMeters(2)


        val d3 = DistanceDepre.ofKilometers(1.0)
        assertTrue(d1 < d2)
        assertTrue(d2 > d1)
        assertTrue(d1 < d3)
        assertTrue(d2 < d3)
    }

    @Test
    fun addition() {
        val d1 = DistanceDepre.ofMeters(1)
        val d2 = DistanceDepre.ofMeters(2)

        assertEquals(DistanceDepre.ofMeters(3), d1 + d2)

    }
    @Test
    fun subtraction() {
        val d1 = DistanceDepre.ofMeters(1)
        val d2 = DistanceDepre.ofMeters(2)

        assertEquals(DistanceDepre.ofMeters(-1), d1 - d2)
        assertEquals(DistanceDepre.ofMeters(1), d2 - d1)
    }
    @Test
    fun multiplicationTest() {
        val distance = (1).toDistance(DistanceUnitDepre.atomic())
        val result = distance * 3
        assertEquals(result.toLong(DistanceUnitDepre.atomic()), 3)
        assertEquals(distance * 4.0, (4).toDistance(DistanceUnitDepre.atomic()))
    }

    @Test
    fun divisionTest() {
        val distance = (1).toDistance(DistanceUnitDepre.METERS)
        assertEquals(distance / 2, (500).toDistance(DistanceUnitDepre.MILLIMETERS))
    }

    @Test
    fun convertingCommaToHighUnit() {
        val test = 42.2.toDistance(DistanceUnitDepre.KILOMETERS)
        val result = test.toDouble(DistanceUnitDepre.KILOMETERS)
        println(result)
    }

    @Test
    fun convenienceConstructors() {
        assertEquals(1L.kilometers, 1.0.kilometers)
        assertEquals(1.kilometers, 1L.kilometers)
        assertEquals(1.0.meters, 1L.meters)
        assertEquals(1.meters, 1L.meters)
        assertEquals(1.kilometers, DistanceDepre.ofKilometers(1))
    }

    @Test
    fun multiplicationCreatesArea() {
        val result = 1.meters * 1.meters
        result / (1.meters)

    }
    @Test
    fun scalarMultiplicationCommutative() {
        val distance = (-1).kilometers
        val a = 3 * distance
        val b = distance * 3
        assertEquals(a, b)
        assertIs<DistanceDepre>(a)
        assertIs<DistanceDepre>(b)
    }
    @Test
    fun absoluteValue() {
        val distance = (-1).kilometers
        assertEquals(abs(distance), 1.kilometers)
    }
}
