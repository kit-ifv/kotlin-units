import org.junit.jupiter.api.Test
import units.Acceleration
import units.Energy
import units.Impulse
import units.Mass
import units.Newton
import units.newton_seconds
import units.Power
import units.Speed
import units.div
import units.kilograms
import units.kmh
import units.meters
import units.meters_per_second
import units.meters_per_second_squared
import units.newton
import units.square_seconds
import units.times
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.seconds

class ConversionTests {
    /**
     * Every combination of types leading to another type would be convenient for users,
     * however way too extensive to implement. Therefore, we implement a subset of possible combinations. This tests
     * tries to list all convenient constructions of types to ensure they are possible.
     * More specifically, each operation which left bracketing is a defined type, should exist.
     */
    
    @Test
    fun massConverions() {
        assertIs<Mass>(1.newton_seconds / 1.kmh)
    }

    @Test
    fun speedConversions() {
        assertIs<Speed>(1.meters / 1.seconds)
        assertIs<Speed>(1 / 1.seconds * 1.meters)
        assertIs<Speed>(1.newton_seconds / 1.kilograms)
    }

    @Test
    fun impulseConverisons() {
        assertIs<Impulse>(1.meters / 1.seconds * 1.kilograms)
        assertIs<Impulse>(1 / 1.seconds * 1.meters * 1.kilograms)
        assertIs<Impulse>(1.meters / 1.seconds * 1.kilograms)
        assertIs<Impulse>(1.kilograms * 1.meters_per_second)
    }

    @Test
    fun accelerationConversions() {
        assertIs<Acceleration>(1.meters / 1.seconds / 1.seconds)
        assertIs<Acceleration>(1.meters / (1.seconds * 1.seconds))
        assertIs<Acceleration>(1.meters / 1.square_seconds)
    }

    @Test
    fun newtonConversions() {
        assertIs<Newton>(1.meters / 1.square_seconds * 1.kilograms)
        assertIs<Newton>(1.meters / 1.seconds / 1.seconds * 1.kilograms)
        assertIs<Newton>(1 / 1.seconds * 1.meters / 1.seconds * 1.kilograms)
        assertIs<Newton>(1.kilograms * 1.meters_per_second_squared)
    }

    @Test
    fun energyConversions() {
        assertIs<Energy>(1.newton * 1.meters)
    }

    @Test
    fun powersConversions() {
        assertIs<Power>(1.newton * 1.meters / 1.seconds)
    }
}