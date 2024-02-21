package units

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds


class PowerTest : GenericUnitTest<PowerUnit, Power>(PowerUnit.values(), Int::toPower, Long::toPower, Double::toPower) {
    @Test
    fun creation() {
        val power = Power.of(1.toEnergy(EnergyUnit.JOULE), 1.seconds)
        assertEquals(1.0, power.rawValue)
    }
}
