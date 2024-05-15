package units

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class CurrencyTest : GenericUnitTest<CurrencyUnit, Currency>(
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
}
