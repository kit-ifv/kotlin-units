import org.junit.jupiter.api.Test
import units.Acceleration
import units.Impulse
import units.Mass
import units.Ns
import units.Speed
import units.div
import units.kilograms
import units.kmh
import units.meters
import units.meters_per_second
import units.square_seconds
import units.times
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.seconds

class ConversionTests {
    /**
     * Every combination of types leading to another type would be convenient for users,
     * however way too extensive to implement. Therefore, we implement a subset of possible combinations. This tests
     * tries to list all convenient constructions of types to ensure they are possible.
     */
    @Test
    fun typeConstructionTests() {
        assertIs<Mass>(1.Ns / 1.kmh)

        assertIs<Speed>(1.meters / 1.seconds)
        assertIs<Speed>(1/1.seconds * 1.meters)
        assertIs<Speed>(1.Ns / 1.kilograms)

        assertIs<Impulse>(1.meters / 1.seconds * 1.kilograms)
        assertIs<Impulse>(1/ 1.seconds * 1.meters * 1.kilograms)
        assertIs<Impulse>(1.meters / 1.seconds * 1.kilograms)
        assertIs<Impulse>(1.kilograms * 1.meters_per_second)

        assertIs<Acceleration>(1.meters/ 1.seconds / 1.seconds)
        assertIs<Acceleration>(1.meters / (1.seconds * 1.seconds))
        assertIs<Acceleration>(1.meters / 1.square_seconds)
    }
}