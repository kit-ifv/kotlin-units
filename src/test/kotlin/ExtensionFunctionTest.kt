import org.junit.jupiter.api.Test
import units.Acceleration
import units.Frequency
import units.Mass
import units.Newton
import units.Temperature
import units.euros
import units.kilograms
import units.kilowatthours
import units.kmh
import units.max
import units.meters
import units.min
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ExtensionFunctionTest {

    @Test
    fun distanceTest() {
        val a = 1.meters
        val b = 2.meters
        val c = 3.meters

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun areaTest() {
        val a = 1.meters * 1.meters
        val b = 2.meters * 1.meters
        val c = 3.meters * 1.meters

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun speedTest() {
        val a = 1.kmh
        val b = 2.kmh
        val c = 3.kmh

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun forceTest() {
        val a = Newton(0.0)
        val b = Newton(2.0)
        val c = Newton(Double.MAX_VALUE)

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun accelerationTest() {
        val a = Acceleration(Double.NEGATIVE_INFINITY)
        val b = Acceleration(2.0)
        val c = Acceleration(Double.MAX_VALUE)

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun currencyTest() {
        val a = 1.euros
        val b = 2.euros
        val c = 3.euros

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun energyTest() {
        val a = 0.kilowatthours
        val b = 2.kilowatthours
        val c = 3.kilowatthours

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun frequencyTest() {
        val a = Frequency(0.0)
        val b = Frequency(50.0)
        val c = Frequency.MAX

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun weightTest() {
        val a = 5.kilograms
        val b = 1000000.kilograms
        val c = Mass(Long.MAX_VALUE)

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun durationTest() {
        val a = 5.seconds
        val b = 1000000.minutes
        val c = 100000000.hours

        minMaxCoerceTest(a, b, c)
    }

    @Test
    fun temperaturTest() {
        val a = Temperature.ABSOLUTE_ZERO
        val b = Temperature.ZERO
        val c = Temperature.MAX

        minMaxCoerceTest(a, b, c)
    }


    fun <T: Comparable<T>> minMaxCoerceTest(min: T, middle: T, max: T) {
        assertEquals(max, max(max,middle))
        assertEquals(min, min(min,middle))
        assertEquals(middle, min.coerceIn(middle,max))
        assertEquals(middle, max.coerceIn(min, middle))
        assertEquals(middle, middle.coerceIn(min,max))
        assertEquals(max, max.coerceIn(min,max))
        assertEquals(max, min.coerceAtLeast(max))
        assertEquals(middle, max.coerceAtMost(middle))
    }
}