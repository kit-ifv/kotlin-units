package unitsOld

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds


class PowerDepreTest : GenericUnitTest<PowerUnitDepre, PowerDepre>(PowerUnitDepre.entries.toTypedArray(), Int::toPower, Long::toPower, Double::toPower) {
    @Test
    fun creation() {
        val power = PowerDepre.of(1.toEnergy(EnergyUnit.JOULE), 1.seconds)
        assertEquals(1.0, power.rawValue)
    }
}
