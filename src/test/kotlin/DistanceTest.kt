import units.kilometers
import units.meters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes


class DistanceTest {
    @Test
    fun example() {
        val distance = 1.meters

        val otherDistance = distance + 1.kilometers
        assertEquals(otherDistance.inMeters, 1001.0)

    }
    @Test
    fun hardDivision() {
        val speed = 1.meters / 1.minutes
        println(speed)

    }
}
