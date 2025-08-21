import org.junit.jupiter.api.Test
import units.Acceleration
import units.Area
import units.CubicDuration
import units.Distance
import units.Energy
import units.Frequency
import units.Impulse
import units.Mass
import units.Newton
import units.newton_seconds
import units.Power
import units.Speed
import units.SquareDuration
import units.Volume
import units.cubic_seconds
import units.div
import units.hertz
import units.joule
import units.kilograms
import units.kmh
import units.meters
import units.meters_per_second
import units.meters_per_second_squared
import units.newton
import units.square_meters
import units.square_seconds
import units.times
import units.watts
import kotlin.test.assertIs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class ConversionTests {
    /**
     * Every combination of types leading to another type would be convenient for users,
     * however way too extensive to implement. Therefore, we implement a subset of possible combinations. This tests
     * tries to list all convenient constructions of types to ensure they are possible.
     * More specifically, each operation which left bracketing is a defined type, should exist.
     * Therefore, testing for every two types and operation (*, /) which yields a valid type should be tested for.
     */

    @Test
    fun distanceConversions() {
        assertIs<Distance>(1.joule / 1.newton)
    }

    @Test
    fun durationConversions() {
        assertIs<Duration>(1.meters / 1.meters_per_second)
        assertIs<Duration>(1.meters_per_second / 1.meters_per_second_squared)
        assertIs<Duration>(1.square_seconds / 1.seconds)
        assertIs<Duration>(1.square_seconds * 1.hertz)
    }

    @Test
    fun squareDurationConversions() {
        assertIs<SquareDuration>(1.seconds * 1.seconds)
        assertIs<SquareDuration>(1.seconds / 1.hertz)
        assertIs<SquareDuration>(1.cubic_seconds / 1.seconds)
        assertIs<SquareDuration>(1.cubic_seconds * 1.hertz)
    }

    @Test
    fun cubicDurationConversions() {
        assertIs<CubicDuration>(1.seconds * 1.square_seconds)
    }

    @Test
    fun areaConversions() {
        assertIs<Area>(1.meters * 1.meters)
    }

    @Test
    fun volumeConversions() {
        assertIs<Volume>(1.meters * 1.square_meters)
    }

    @Test
    fun speedConversions() {
        assertIs<Speed>(1.meters / 1.seconds)
        assertIs<Speed>(1.newton_seconds / 1.kilograms)
    }

    @Test
    fun accelerationConversions() {
        assertIs<Acceleration>(1.meters_per_second / 1.seconds)
        assertIs<Acceleration>(1.newton / 1.kilograms)
    }

    @Test
    fun impulseConversions() {
        assertIs<Impulse>(1.kilograms * 1.meters_per_second)
        assertIs<Impulse>(1.newton * 1.seconds)
    }

    @Test
    fun newtonConversions() {
        assertIs<Newton>(1.kilograms * 1.meters_per_second_squared)
        assertIs<Newton>(1.joule / 1.meters)
        assertIs<Newton>(1.newton_seconds / 1.seconds)
    }

    @Test
    fun energyConversions() {
        assertIs<Energy>(1.newton * 1.meters)
        assertIs<Energy>(1.watts * 1.seconds)
    }

    @Test
    fun powerConversions() {
        assertIs<Power>(1.joule / 1.seconds)
    }

    @Test
    fun frequencyConversions() {
        assertIs<Frequency>(1 / 1.seconds)
        assertIs<Frequency>(1.watts / 1.joule)
    }

    @Test
    fun massConversions() {
        assertIs<Mass>(1.newton / 1.meters_per_second_squared)
        assertIs<Mass>(1.newton_seconds / 1.meters_per_second)
    }
}