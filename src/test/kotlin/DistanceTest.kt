import units.kilometers
import units.meters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes


class DistanceTest {
    @Test
    fun example() {
        val distance = 1.meters
        val otherDistance = 1.11111.meters
        assertEquals(otherDistance.inWholeMeters, 1)
        assertEquals(otherDistance.inWholeCentimeters, 111)
        assertEquals(otherDistance.inWholeMillimeters, 1111)
        assertEquals(otherDistance.inWholeKilometers, 0)
        assertEquals(otherDistance.inMeters, 1.11111)
        assertEquals(otherDistance.inKilometers, .00111111)

    }
    @Test
    fun hardDivision() {
        val speed = 1.meters / 1.minutes
        println(speed)

    }
}
