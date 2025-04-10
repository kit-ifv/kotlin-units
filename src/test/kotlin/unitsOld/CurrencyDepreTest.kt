package unitsOld

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class CurrencyDepreTest : GenericUnitTest<CurrencyUnit, CurrencyDepre>(
    CurrencyUnit.entries.toTypedArray(),
    Int::toCurrency,
    Long::toCurrency,
    Double::toCurrency
) {
    @Test
    fun addingMoney() {
        val money = 1.euros
        assertEquals(2.euros, money + money)
    }
    @Test
    fun comparingMoney() {
        assertTrue(1.euros < 2.euros)
        assertTrue(3.euros > 2.5.euros)
    }
}
