import units.Distance
import units.average
import units.kilometers
import units.meters
import units.sumOf
import units.times
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes


class DistanceTest {
    @Test
    fun example() {
        val distance = 1.meters

        val otherDistance = distance + 1.kilometers
        assertEquals(otherDistance.inMeters, 1001.0)
        assertEquals(-distance.inMeters, -1.0)
        assertEquals((3 * distance).inMeters, 3.0)
        assertEquals((3.0 * distance).inMeters, 3.0)
        assertEquals((distance *3.0).inMeters, 3.0)

    }
    @Test
    fun iterables() {
        val list = (1..10).map { it.meters }
        assertEquals(list.min(), 1.meters)
        assertEquals(list.max(), 10.meters)
        assertEquals(list.average(), 5.5.meters)
        assertEquals(list.sumOf { it }, 55.meters)

    }
}
