package units

import kotlin.test.Test
import kotlin.test.assertEquals

class ConverterTest {
    @Test
    fun coordinateConversion() {

        val result = Converter.toUtm(0.0, 0.0)
        assertEquals(166021.443, result.e, absoluteTolerance = 0.01)
        assertEquals(0.0, result.n, absoluteTolerance = 1.0)
        assertEquals(31, result.zone)
        assertEquals(Hemisphere.NORTHERN, result.hemisphere)

    }


    @Test
    fun utmToDegree() {
        val d= Converter.toWGS84(500000.0, 600000.0,  34, Hemisphere.NORTHERN)
        assertEquals(5.428225, d.latitudeDegrees, absoluteTolerance = 0.00001)
        assertEquals(21.0, d.longitudeDegrees, absoluteTolerance = 0.00001)

    }

    @Test
    fun utmToDegree2() {
        val d= Converter.toWGS84(210590.347, 3322575.904,  31, Hemisphere.NORTHERN)
        assertEquals(30.0, d.latitudeDegrees, absoluteTolerance = 0.00001)
        assertEquals(0.0, d.longitudeDegrees, absoluteTolerance = 0.00001)

    }

    @Test
    fun utmToDegree3() {
        val d= Converter.toWGS84(600000.0, 600000.0, 33, Hemisphere.NORTHERN)
        assertEquals(5.427551, d.latitudeDegrees, absoluteTolerance = 0.00001)
        assertEquals(15.902658, d.longitudeDegrees, absoluteTolerance = 0.00001)

    }

    @Test
    fun utmToDegree4() {
        val d= Converter.toWGS84(460000.0, 6000000.0, 47,  Hemisphere.NORTHERN)
        assertEquals(54.146547, d.latitudeDegrees, absoluteTolerance = 0.00001)
        assertEquals(98.387618, d.longitudeDegrees, absoluteTolerance = 0.00001)

    }
    @Test
    fun degreeToUtmNorway() {
        val result = Converter.toUtm(60.0, 5.0)
        assertEquals(276979.926, result.e, absoluteTolerance = 0.01)
        assertEquals(6658157.202, result.n, absoluteTolerance = 1.0)
        assertEquals(32, result.zone)
        assertEquals(Hemisphere.NORTHERN, result.hemisphere)
    }
    @Test
    fun identity() {
        val coordinate = Pair(0.0, 0.0).toCoordinate()
        assertEquals(coordinate.latitudeDegrees, coordinate.toUTM().toWGS84().latitudeDegrees, absoluteTolerance = 0.00001)
        assertEquals(coordinate.longitudeDegrees, coordinate.toUTM().toWGS84().longitudeDegrees, absoluteTolerance = 0.00001)
    }

    @Test
    fun differentCreations() {
        val d = GPSCoordinate.decimalDegree(55.51, 55.51)
        val d2 = GPSCoordinate.degreesMinutes(55, 30.6, 55, 30.6)
        val d3 = GPSCoordinate.degreesMinutesSeconds(
            55, 30, 36.0,
            55, 30, 36.0)

        assertEquals(d, d2)
        assertEquals(d2, d3)
    }

}
