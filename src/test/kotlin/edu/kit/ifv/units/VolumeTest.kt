package edu.kit.ifv.units

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VolumeTest {
    @Test
    fun testConversionArea() {
        val volume = 1.cubicMeters
        val area = volume / 10.meters
        assertEquals(area, 0.1.meters * 1.meters)
    }
}