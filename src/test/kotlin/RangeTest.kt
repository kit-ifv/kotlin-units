import edu.kit.ifv.units.Acceleration
import edu.kit.ifv.units.Frequency
import edu.kit.ifv.units.Mass
import edu.kit.ifv.units.Power
import edu.kit.ifv.units.Temperature
import edu.kit.ifv.units.cubicMeters
import edu.kit.ifv.units.cubicSeconds
import edu.kit.ifv.units.degrees
import edu.kit.ifv.units.euros
import edu.kit.ifv.units.kilograms
import edu.kit.ifv.units.kilowatthours
import edu.kit.ifv.units.kmh
import edu.kit.ifv.units.max
import edu.kit.ifv.units.meters
import edu.kit.ifv.units.metersPerSecond
import edu.kit.ifv.units.min
import edu.kit.ifv.units.newton
import edu.kit.ifv.units.newtonSeconds
import edu.kit.ifv.units.radians
import edu.kit.ifv.units.squareSeconds
import edu.kit.ifv.units.wrap
import org.junit.jupiter.api.Test
import kotlin.assert
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class RangeTest {

    @Test
    fun distanceTest() {
        val a = 1.meters
        val b = 2.meters
        val c = 3.meters

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun areaTest() {
        val a = 1.meters * 1.meters
        val b = 2.meters * 1.meters
        val c = 3.meters * 1.meters

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun speedTest() {
        val a = 1.kmh
        val b = 2.kmh
        val c = 3.kmh

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun impulseTest() {
        val a = 0.0.newtonSeconds
        val b = 2.0.newtonSeconds
        val c = Double.MAX_VALUE.newtonSeconds

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun forceTest() {
        val a = 0.0.newton
        val b = 2.0.newton
        val c = Double.MAX_VALUE.newton

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun accelerationTest() {
        val a = Acceleration(Double.NEGATIVE_INFINITY)
        val b = Acceleration(2.0)
        val c = Acceleration(Double.MAX_VALUE)

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun currencyTest() {
        val a = 1.euros
        val b = 2.euros
        val c = 3.euros

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun energyTest() {
        val a = 0.kilowatthours
        val b = 2.kilowatthours
        val c = 3.kilowatthours

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun powerTest() {
        val a = Power(2.0)
        val b = Power(3.0)
        val c = Power(Double.MAX_VALUE)

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun frequencyTest() {
        val a = Frequency(0.0)
        val b = Frequency(50.0)
        val c = Frequency.MAX

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun massTest() {
        val a = 5.kilograms
        val b = 1000000.kilograms
        val c = Mass(Long.MAX_VALUE)

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun durationTest() {
        val a = 5.seconds
        val b = 1000000.minutes
        val c = 100000000.hours

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun durationWrapperTest() {
        val a = 5.seconds.wrap
        val b = 1000000.minutes.wrap
        val c = 100000000.hours.wrap

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)

    }


    @Test
    fun squareDurationTest() {
        val a = 5.squareSeconds
        val b = 1000000.squareSeconds
        val c = 100000000.squareSeconds

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun cubicDurationTest() {
        val a = 5.cubicSeconds
        val b = 1000000.cubicSeconds
        val c = 100000000.cubicSeconds

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun temperaturTest() {
        val a = Temperature.ABSOLUTE_ZERO
        val b = Temperature.ZERO
        val c = Temperature(1000)

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun volumeTest() {
        val a = 0.cubicMeters
        val b = 2000.cubicMeters
        val c = 780980928.cubicMeters

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun radiansTest() {
        val a = 0.radians
        val b = 2000.radians
        val c = 780980928.radians

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }

    @Test
    fun degreesTest() {
        val a = 0.9.degrees
        val b = 2000.degrees
        val c = 780980928.degrees

        assertEquals(c, max(c,b))
        assertEquals(a, min(a,b))
        assertEquals(b, a.coerceIn(b,c))
        assertEquals(b, c.coerceIn(a, b))
        assertEquals(b, b.coerceIn(a,c))
        assertEquals(c, c.coerceIn(a,c))
        assertEquals(c, a.coerceAtLeast(c))
        assertEquals(b, c.coerceAtMost(b))
        assert(b in a..b) // rangeTo and rangeUntil operator
        assertFalse(b in a..<b)
    }
}