package unitsOld

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class EfficiencyDepreTest {

    @Test
    fun getMyLiters() {
        val efficiency = 8.litersBenzene / 100.kilometers

        val drive = efficiency * 50.kilometers

        val consumedBenzene = drive.asLitersBenzene
        assertEquals(consumedBenzene, 4.liters)
    }
    @Test
    fun getTheDistance() {

        val capacity = 80.litersBenzene
        val efficiency = 8.litersBenzene / 100.kilometers

        val distance = capacity / efficiency
        assertEquals(distance, 1000.kilometers)
    }


}