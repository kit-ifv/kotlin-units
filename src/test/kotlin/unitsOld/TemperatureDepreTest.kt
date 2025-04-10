package unitsOld


import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue


class TemperatureDepreTest : GenericUnitTest<TempUnitDepre, TemperatureDepre>(
    TempUnitDepre.entries.toTypedArray(),
    Int::toTemperature,
    Long::toTemperature,
    Double::toTemperature
) {
    @Test
    fun absoluteZero() {
        val absZero = 0.toTemperature(TempUnitDepre.KELVIN)
        assertTrue((-459.67).toTemperature(TempUnitDepre.FAHRENHEIT).rawValue < 1000)
        assertEquals(absZero, (-273.15).toTemperature(TempUnitDepre.CELSIUS))

    }

    @Test
    fun lukewarmBody() {
        val temperature = 100.toTemperature(TempUnitDepre.FAHRENHEIT)

        val kelvin = 310.928.toTemperature(TempUnitDepre.KELVIN)
        val celsius = 37.7778.toTemperature(TempUnitDepre.CELSIUS)
        assertTrue((temperature - kelvin).rawValue < 1000)
        assertTrue((temperature - celsius).rawValue < 1000)
    }

    @Test
    fun freezingPointOfBrine() {
        val celsius = (-17.7778).toTemperature(TempUnitDepre.CELSIUS)
        val temperature = 0.toTemperature(TempUnitDepre.FAHRENHEIT)
        val kelvin = 255.372.toTemperature(TempUnitDepre.KELVIN)
        assertTrue((temperature - kelvin).rawValue < 1000)
        assertTrue((temperature - celsius).rawValue < 1000)
    }

    @Test
    fun meltingPointOfWater() {
        val temperature = 100.toTemperature(TempUnitDepre.CELSIUS)
        val kelvin = 373.15.toTemperature(TempUnitDepre.KELVIN)
        val celsius = 212.toTemperature(TempUnitDepre.FAHRENHEIT)
        assertTrue((temperature - kelvin).rawValue < 1000)
        assertTrue((temperature - celsius).rawValue < 1000)
    }

    @Test
    fun addition() {
        val t = 1.toTemperature(TempUnitDepre.KELVIN)
        assertEquals(2, (t + t).toLong(TempUnitDepre.KELVIN))
    }

    @Test
    fun subtraction() {
        val t = 2.toTemperature(TempUnitDepre.KELVIN)
        assertEquals(0, (t - t).toLong(TempUnitDepre.KELVIN))
    }

    //Temperature has an offset and thusly the conversion check for 42 and 21 is not one half
    @TestFactory
    override fun inverseElement(): List<DynamicTest> {
        return a.map {
            DynamicTest.dynamicTest(a.toString())  {
            val element = doubleConverter(42.0, it)
            val div = element / element
            assertIs<Double>(div)
            assertEquals(1.0, div)
        }

        }

    }
}
