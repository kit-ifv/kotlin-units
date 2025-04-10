package unitsOld

import kotlin.test.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class HigherOrderUnitTest {
    @Test
    fun building() {


        val energy: EnergyDepre = 10.toEnergy(EnergyUnit.JOULE)
        val distance: DistanceDepre = 10.toDistance(DistanceUnitDepre.KILOMETERS)
        val watts = 2.watts
        val test = watts * 1.seconds
        val ek = test * watts

        val t = HigherOrderUnit(listOf(energy, distance))
        val t2 = HigherOrderUnit(energy, distance)
        val t4 = t2 * t2
        val target = t.resolve(listOf(DistanceUnitDepre.KILOMETERS, EnergyUnit.JOULE))
        val awe = listOf(energy, Duration.INFINITE)
  