package unitsOld

/**
 * Raw value should be energy needed to traverse 1 meter
 */
@JvmInline
value class EfficiencyDepre(override val rawValue: Double): FloatUnit<EfficiencyUnit>, ScalarUnit<EfficiencyUnit> {
    override fun plus(other: FloatUnit<EfficiencyUnit>): EfficiencyDepre {
        return EfficiencyDepre(rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<EfficiencyUnit>): EfficiencyDepre {
        return EfficiencyDepre(rawValue - other.rawValue)
    }

    override fun unaryMinus(): EfficiencyDepre {
        return EfficiencyDepre(-rawValue)
    }
    override fun times(scalar: Double): EfficiencyDepre {
        return EfficiencyDepre(rawValue * scalar)
    }

    override fun div(scalar: Double): EfficiencyDepre {
        return EfficiencyDepre(rawValue / scalar)
    }
    /**
     * How much energy is needed to traverse the given distance
     */
    operator fun times(other: DistanceDepre): EnergyDepre {
        return EnergyDepre(rawValue * other.toDouble(DistanceUnitDepre.METERS))
    }
    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = mapOf(EnergyDepre::class.java to 3, DistanceDepre::class.java to -1)

}
operator fun Number.times(element: EfficiencyDepre): EfficiencyDepre {
    return element * this.toDouble()
}
val Pair<EnergyDepre, DistanceDepre>.efficiencyDepre: EfficiencyDepre
    get() {
        return first / second
    }


/**
 * I am unaware that there are scales for Efficiency in any meaningful way.
 */
enum class EfficiencyUnit(override val scale: Double) : FloatUnitScale {
    DEFAULT(1.0),



}
