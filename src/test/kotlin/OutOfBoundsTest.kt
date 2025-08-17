import org.junit.jupiter.api.Test
import units.Ns
import units.PhysicsUnit
import units.cubicMeters
import units.cubic_seconds
import units.div
import units.joule
import units.kilograms
import units.kmh
import units.meters
import units.newton
import units.square_seconds
import units.square_meters
import units.toOutOfBoundsUnit
import units.watts
import units.wrap
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class OutOfBoundsTest {
    @Test
    fun correctExponentsForBaseUnits() {
        assertEquals(PhysicsUnit(1,0,0),
            1.meters.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(0,1,0),
                1.seconds.wrap.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(0,0,1),
            1.kilograms.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(1,-1, 0),
            1.kmh.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(1,-1, 1),
            1.Ns.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(1, -2, 1),
            1.0.newton.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(1, -1, 1),
            1.Ns.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(0, -1, 0),
            (1 / 1.seconds).toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(2, -2, 1),
            1.0.joule.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(2, -3, 1),
            1.watts.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(3, 0, 0),
            1.cubicMeters.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(2, 0, 0),
            1.square_meters.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(1, -2, 0),
            ((1.meters / 1.seconds ) / 1.seconds) .toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(0, 2, 0),
            1.square_seconds.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(0, 3, 0),
            1.cubic_seconds.toOutOfBoundsUnit().unit)
    }
}