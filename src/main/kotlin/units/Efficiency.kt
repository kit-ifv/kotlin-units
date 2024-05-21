package units

/**
 * Raw value should be energy needed to traverse 1 meter
 */
@JvmInline
value class Efficiency(override val rawValue: Double): FloatUnit<EfficiencyUnit>, ScalarUnit<EfficiencyUnit> {
    override fun plus(other: FloatUnit<EfficiencyUnit>): Efficiency {
        return Efficiency(rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<EfficiencyUnit>): Efficiency {
        return Efficiency(rawValue - other.rawValue)
    }

    override fun unaryMinus(): Efficiency {
        return Efficiency(-rawValue)
    }
    override fun times(scalar: Double): Efficiency {
        return Efficiency(rawValue * scalar)
    }
    override fun times(scalar: Number): Efficiency {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): Efficiency {
        return this / scalar.toDouble()
    }


    override fun div(scalar: Double): Efficiency {
        return Efficiency(rawValue / scalar)
    }
    /**
     * How much energy is needed to traverse the given distance
     */
    operator fun times(other: Distance): Energy {
        return Energy(rawValue * other.toDouble(DistanceUnit.METERS))
    }
    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = mapOf(Energy::class.java to 3, Distance::class.java to -1)

}
operator fun Number.times(element: Efficiency): Efficiency {
    return element * this.toDouble()
}
val Pair<Energy, Distance>.efficiency: Efficiency
    get() {
        return first / second
    }


/**
 * I am unaware that there are scales for Efficiency in any meaningful way.
 */
enum class EfficiencyUnit(override val scale: Double) : FloatUnitScale {
    DEFAULT(1.0),



}
