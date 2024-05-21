package units

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class EfficiencyTest {

    @Test
    fun getMyLiters() {
        val efficiency = 8.litersBenzene / 100.kilometers

        val drive = efficiency * 50.kilometers

        val consumedBenzene = drive.asLitersBenzene
        assertEquals(consumedBenzene, 4.liters)
    }
}