package unitsOld

import kotlin.time.Duration
import kotlin.time.DurationUnit


@JvmInline
value class DurationImitator(override val rawValue: Double) : FloatUnit<DurationImitatorUnit>, ScalarUnit<DurationImitatorUnit> {
    override fun plus(other: FloatUnit<DurationImitatorUnit>): DurationImitator {
        return DurationImitator(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<DurationImitatorUnit>): DurationImitator {
        return this + (-other)
    }

    override fun unaryMinus(): DurationImitator {
        return DurationImitator(-rawValue)
    }


    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = mapOf(DurationImitator::class.java to 1)

    override fun times(scalar: Double): DurationImitator {
        return DurationImitator(rawValue * scalar)
    }
    override fun times(scalar: Number): DurationImitator {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): DurationImitator {
        return this * scalar.toDouble()
    }

    override fun div(scalar: Double): DurationImitator {
        return DurationImitator(rawValue / scalar)
    }
}
operator fun Number.times(element: DurationImitator): DurationImitator {
    return element * this.toDouble()
}
fun Double.toFakeDuration(units: DurationImitatorUnit): DurationImitator {
    return DurationImitator(this * units.scale)
}

inline val Duration.fakeDuration: DurationImitator
    get() = this.toDouble(DurationUnit.SECONDS).toFakeDuration(DurationImitatorUnit.SECONDS)

enum class DurationImitatorUnit(override val scale: Double) : FloatUnitScale {
    SECONDS(1.0);
}