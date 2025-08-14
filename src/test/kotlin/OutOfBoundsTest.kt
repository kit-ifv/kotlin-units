import org.junit.jupiter.api.Test
import units.Acceleration
import units.Newton
import units.Ns
import units.PhysicsUnit
import units.Speed
import units.cubicMeters
import units.cubicSeconds
import units.div
import units.joule
import units.kilograms
import units.kmh
import units.meters
import units.newton
import units.squareMeters
import units.squareSeconds
import units.times
import units.toOutOfBoundsUnit
import units.watts
import kotlin.test.assertEquals
import kotlin.test.assertIs
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
        assertEquals(PhysicsUnit(0, 2, 0),
            1.squareSeconds.toOutOfBoundsUnit().unit)
        assertEquals(PhysicsUnit(0, 3, 0),
            1.cubicSeconds.toOutOfBoundsUnit().unit)
    }


    /**
     * Every combination of types leading to another type would be convenient for users,
     * however way too extensive to implement. Therefore, we implement a subset of possible combinations. This tests
     * tries to list all convenient constructions of types to ensure they are possible.
     */
    @Test
    fun typeConstructionTests() {
        assertIs<Speed>(1.meters / 1.seconds)
        assertIs<Acceleration>(1.meters/ 1.seconds / 1.seconds)
        assertIs<Acceleration>(1.meters / (1.seconds * 1.seconds))

    }
}