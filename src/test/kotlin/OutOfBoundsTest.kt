import org.junit.jupiter.api.Test
import units.Newton
import units.Ns
import units.PhysicsUnit
import units.cubicMeters
import units.div
import units.joule
import units.kilograms
import units.kmh
import units.meters
import units.newton
import units.squareMeters
import units.toOutOfBoundsUnit
import units.watts
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class OutOfBoundsTest {
    @Test
    fun correctPhysicsUnit() {
        assertEquals(PhysicsUnit(1,0,0),
            1.meters.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(0,1,0),
                1.seconds.toOutOfBoundsUnit().unit)
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
            1.squareMeters.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(1, -2, 0),
            ((1.meters / 1.seconds ) / 1.seconds) .toOutOfBoundsUnit().unit)

    }
}