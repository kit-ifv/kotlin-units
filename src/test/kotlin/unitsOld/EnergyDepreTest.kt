package unitsOld

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds


class EnergyDepreTest :
    GenericUnitTest<EnergyUnit, EnergyDepre>(
        EnergyUnit.entries.toTypedArray(),
        Int::toEnergy,
        Long::toEnergy,
        Double::toEnergy
    ) {
    @Test
    fun creation() {
        val energy = EnergyDepre.of(1.kilograms, 1.meters, 1.seconds)
        assertEquals(1.0, energy.rawValue)
    }

    @Test
    fun kilowatthour() {
        assertEquals(1.toEnergy(EnergyUnit.KILOWATTHOUR), 3600000.toEnergy(EnergyUnit.JOULE))
    }

}
