package units

import kotlin.math.roundToLong

@JvmInline
value class Mass(override val rawValue: Long) : LongUnit<MassUnit>, ScalarUnit<MassUnit> {

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(Mass::class.java to 1)
    }

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    override fun unaryMinus(): Mass {
        return Mass(-rawValue)
    }


    override fun times(scalar: Double): Mass {
        return Mass((scalar * rawValue).roundToLong())
    }

    override fun times(scalar: Number): Mass {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): Mass {
        return this / scalar.toDouble()
    }
    override fun div(scalar: Double): Mass {
        return Mass((rawValue / scalar).roundToLong())
    }

    override fun minus(other: LongUnit<MassUnit>): Mass {
        return this + (-other)
    }

    override fun plus(other: LongUnit<MassUnit>): Mass {
        return Mass(rawValue + other.rawValue)
    }

}

enum class MassUnit(override val scale: Long) : LongUnitScale {
    MICROGRAM(1L),
    MILLIGRAM(1000L),
    GRAM(1_000_000L),
    KILOGRAM(1_000_000_000L),
    TON(1_000_000_000_000L);
}

/**
 * These extension functions provide basic functionality to convert to mass with a unit. Common use cases should be
 * implemented as convenience extension function in the setting below
 */
fun Int.toMass(unit: MassUnit): Mass {
    return Mass(convertUnit(toLong(), unit.scale))
}

fun Long.toMass(unit: MassUnit): Mass {
    return Mass(convertUnit(this, unit.scale))
}

fun Double.toMass(unit: MassUnit): Mass {
    val x = (this * unit.scale).roundToLong()
    return Mass(convertUnit(x, 1L))
}

fun Number.toMass(unit: MassUnit): Mass {
    return toDouble().toMass(unit)
}

operator fun Number.times(element: Mass): Mass {
    return element * this.toDouble()
}
inline val Number.grams: Mass
    get() = this.toMass(MassUnit.GRAM)

inline val Number.kilograms: Mass
    get() = this.toMass(MassUnit.KILOGRAM)



