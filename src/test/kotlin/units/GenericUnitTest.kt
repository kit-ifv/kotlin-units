package units

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class GenericUnitTest<T : NumericUnitScale, S : NumericUnit<T>>
    (
    val a: Array<T>,
    private val intConverter: (i: Int, unit: T) -> S,
    private val longConverter: (i: Long, unit: T) -> S,
    val doubleConverter: (i: Double, unit: T) -> S
) {


    companion object {
        val TEST_INTEGERS = listOf(1, 2, 3, 42, -1337, 9001)
        val TEST_LONGS = listOf(1L, 2L, 3L, 42L, -1337L, 9001L)
        val TEST_DOUBLES = listOf(1.0, 2.0, 3.0, 42.0, -1337.0, 9001.0)
    }

    @Test
    fun integers() {
        integerConversion(TEST_INTEGERS, a, intConverter)
    }

    @Test
    fun longs() {
        longConversion(TEST_LONGS, a, longConverter)
    }

    @Test
    fun doubles() {
        doubleConversion(TEST_DOUBLES, a, doubleConverter)
    }
    @TestFactory
    open fun inverseElement(): List<DynamicTest> {
        return a.map {DynamicTest.dynamicTest(a.toString())  {
            val element = doubleConverter(42.0, it)
            val secondElement = doubleConverter(21.0, it)
            val div = element / element
            assertIs<Double>(div)
            assertEquals(1.0, div)
            assertEquals(2.0, element / secondElement)
        }

        }

    }
    private fun <T : NumericUnitScale, S : NumericUnit<T>> integerConversion(
        testVals: Collection<Int>, a: Array<T>, foo: (i: Int, unit: T) -> S
    ) {
        for (i in testVals) {
            for (unit in a) {
                val s = foo(i, unit)
                assertEquals(i, s.toInt(unit))
            }
        }
    }

    private fun <T : NumericUnitScale, S : NumericUnit<T>> longConversion(
        testVals: Collection<Long>,
        a: Array<T>,
        foo: (i: Long, unit: T) -> S
    ) {
        for (i in testVals) {
            for (unit in a) {
                val s = foo(i, unit)
                assertEquals(i, s.toLong(unit))
            }
        }
    }

    private fun <T : NumericUnitScale, S : NumericUnit<T>> doubleConversion(
        testVals: Collection<Double>,
        a: Array<T>,
        foo: (i: Double, unit: T) -> S
    ) {
        for (i in testVals) {
            for (unit in a) {
                val s = foo(i, unit)
                assertEquals(i, s.toDouble(unit), 0.000001)
            }
        }
    }

}
