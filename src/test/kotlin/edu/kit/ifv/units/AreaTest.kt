package edu.kit.ifv.units

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AreaTest {
    @Test
    fun conversionDistance() {
        val distance = 1.kilometers
        val area = distance * distance
        assertEquals((area / 10.meters), 100.kilometers)
    }
    @Test
    fun conversionVolume() {
        val distance = 10.meters
        val area = distance * distance

        val volume = area * 1.meters
        assertEquals(volume, 100.cubicMeters)
    }
}