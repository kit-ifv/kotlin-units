
import units.kmh
import units.meters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

class SpeedTest {
    @Test
    fun conversion() {
        val speed = 1.kmh
        assertEquals(speed * 30.minutes, 500.meters)

    }
}