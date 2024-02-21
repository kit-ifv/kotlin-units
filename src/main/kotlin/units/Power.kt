package units

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@JvmInline
value class Power(override val rawValue: Double) : FloatUnit<PowerUnit> {

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> =
            mapOf(Mass::class.java to 1, Distance::class.java to 2, DurationImitator::class.java to -3)

        fun of(energy: Energy, duration: Duration): Power {
            return Power(energy.toDouble(EnergyUnit.JOULE) / duration.toDouble(DurationUnit.SECONDS))
        }
    }

    override operator fun times(duration: Duration): Energy {
        return Energy(this.rawValue * duration.toDouble(DurationUnit.SECONDS))
    }

    override fun plus(other: FloatUnit<PowerUnit>): Power {
        return Power(rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<PowerUnit>): Power {
        return this + (-other)
    }

    override fun unaryMinus(): Power {
        return Power(-rawValue)
    }

    override operator fun times(other: NumericUnit<*>): HigherOrderUnit {
        return HigherOrderUnit(
            listOf(rawValue.toMass(MassUnit.KILOGRAM), 1.meters, 1.meters, other),
            listOf(1.seconds.fakeDuration, 1.seconds.fakeDuration)
        )
    }
}

fun Int.toPower(units: PowerUnit): Power {
    return Power(this * units.scale)
}


fun Long.toPower(units: PowerUnit): Power {
    return Power(this * units.scale)
}

fun Double.toPower(units: PowerUnit): Power {
    return Power(this * units.scale)
}

inline val Number.watts: Power
    get() = this.toDouble().toPower(PowerUnit.WATTS)

enum class PowerUnit(override val scale: Double) : FloatUnitScale {
    WATTS(1.0),
    KILOWATTS(1000.0)
}
