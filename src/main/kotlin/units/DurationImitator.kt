package units

import kotlin.time.Duration
import kotlin.time.DurationUnit


@JvmInline
value class DurationImitator(override val rawValue: Double) : FloatUnit<DurationImitatorUnit> {
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
}

fun Double.toFakeDuration(units: DurationImitatorUnit): DurationImitator {
    return DurationImitator(this * units.scale)
}

inline val Duration.fakeDuration: DurationImitator
    get() = this.toDouble(DurationUnit.SECONDS).toFakeDuration(DurationImitatorUnit.SECONDS)

enum class DurationImitatorUnit(override val scale: Double) : FloatUnitScale {
    SECONDS(1.0);
}