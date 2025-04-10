package unitsOld


import kotlin.test.Test
import kotlin.test.assertEquals


class AreaDepreTest : GenericUnitTest<AreaUnitDepre, AreaDepre>(AreaUnitDepre.entries.toTypedArray(), Int::toArea, Long::toArea, Double::toArea) {
    @Test
    fun creation() {
        val areaDepre = AreaDepre.ofRectangle(1.meters, 1.meters)

        val test = areaDepre.toDouble(AreaUnitDepre.SQUARE_KILOMETERS)
        assertEquals(0.000001, test)

    }

    @Test
    fun multiplicationWithDistance() {
        val areaDepre = AreaDepre.ofRectangle(1.meters, 1.meters)
        val cube = areaDepre * 1.meters
        val divisor = cube / 1.meters
        assertEquals(cube, VolumeDepre.ofCube(1.meters, 1.meters, 1.meters))
        assertEquals(areaDepre, divisor)
    }
}
