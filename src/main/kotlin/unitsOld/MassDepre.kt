package unitsOld

import kotlin.math.roundToLong

@JvmInline
value class MassDepre(override val rawValue: Long) : LongUnit<MassUnit>, ScalarUnit<MassUnit> {

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(MassDepre::class.java to 1)
    }

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    override fun unaryMinus(): MassDepre {
        return MassDepre(-rawValue)
    }

    override fun minus(other: LongUnit<MassUnit>): MassDepre {
        return this + (-other)
    }

    override fun times(scalar: Double): MassDepre {
        return MassDepre((scalar * rawValue).roundToLong())
    }

    override fun times(scalar: Number): MassDepre {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): MassDepre {
        return this / scalar.toDouble()
    }
    override fun div(scalar: Double): MassDepre {
        return MassDepre((rawValue / scalar).roundToLong())
    }


    override fun plus(other: LongUnit<MassUnit>): MassDepre {
        return MassDepre(rawValue + other.rawValue)
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
fun Int.toMass(unit: MassUnit): MassDepre {
    return MassDepre(convertUnit(toLong(), unit.scale))
}

fun Long.toMass(unit: MassUnit): MassDepre {
    return MassDepre(convertUnit(this, unit.scale))
}

fun Double.toMass(unit: MassUnit): MassDepre {
    val x = (this * unit.scale).roundToLong()
    return MassDepre(convertUnit(x, 1L))
}

fun Number.toMass(unit: MassUnit): MassDepre {
    return toDouble().toMass(unit)
}

operator fun Number.times(element: MassDepre): MassDepre {
    return element * this.toDouble()
}
inline val Number.grams: MassDepre
    get() = this.toMass(MassUnit.GRAM)

inline val Number.kilograms: MassDepre
    get() = this.toMass(MassUnit.KILOGRAM)



