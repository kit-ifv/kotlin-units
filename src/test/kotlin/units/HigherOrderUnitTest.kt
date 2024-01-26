package units

import kotlin.test.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class HigherOrderUnitTest {
    @Test
    fun building() {


        val energy: Energy = 10.toEnergy(EnergyUnit.JOULE)
        val distance: Distance = 10.toDistance(DistanceUnit.KILOMETERS)
        val watts = 2.watts
        val test = watts * 1.seconds
        val ek = test * watts

        val t = HigherOrderUnit(listOf(energy, distance))
        val t2 = HigherOrderUnit(energy, distance)
        val t4 = t2 * t2
        val target = t.resolve(listOf(DistanceUnit.KILOMETERS, EnergyUnit.JOULE))
        val awe = listOf(energy, Duration.INFINITE)
    }


}