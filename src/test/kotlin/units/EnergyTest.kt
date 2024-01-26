package units

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds


class EnergyTest :
    GenericUnitTest<EnergyUnit, Energy>(
        EnergyUnit.values(),
        Int::toEnergy,
        Long::toEnergy,
        Double::toEnergy
    ) {
    @Test
    fun creation() {
        val energy = Energy.of(1.kilograms, 1.meters, 1.seconds)
        assertEquals(1.0, energy.rawValue)
    }

    @Test
    fun kilowatthour() {
        assertEquals(1.toEnergy(EnergyUnit.KILOWATTHOUR), 3600000.toEnergy(EnergyUnit.JOULE))
    }

}
