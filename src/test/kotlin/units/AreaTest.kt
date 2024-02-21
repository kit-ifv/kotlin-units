package units


import units.Area
import units.AreaUnit
import kotlin.test.Test
import kotlin.test.assertEquals


class AreaTest : GenericUnitTest<AreaUnit, Area>(AreaUnit.values(), Int::toArea, Long::toArea, Double::toArea) {
    @Test
    fun creation() {
        val area = Area.ofRectangle(1.meters, 1.meters)

        val test = area.toDouble(AreaUnit.SQUARE_KILOMETERS)
        assertEquals(0.000001, test)

    }
}
