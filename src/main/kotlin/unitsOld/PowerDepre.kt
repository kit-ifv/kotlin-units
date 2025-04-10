package unitsOld

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@JvmInline
value class PowerDepre(override val rawValue: Double) : FloatUnit<PowerUnitDepre>, ScalarUnit<PowerUnitDepre> {

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> =
            mapOf(MassDepre::class.java to 1, DistanceDepre::class.java to 2, DurationImitator::class.java to -3)

        fun of(energy: EnergyDepre, duration: Duration): PowerDepre {
            return PowerDepre(energy.toDouble(EnergyUnit.JOULE) / duration.toDouble(DurationUnit.SECONDS))
        }
    }

    override operator fun times(duration: Duration): EnergyDepre {
        return EnergyDepre(this.rawValue * duration.toDouble(DurationUnit.SECONDS))
    }

    override fun plus(other: FloatUnit<PowerUnitDepre>): PowerDepre {
        return PowerDepre(rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<PowerUnitDepre>): PowerDepre {
        return this + (-other)
    }

    override fun unaryMinus(): PowerDepre {
        return PowerDepre(-rawValue)
    }

    override operator fun times(other: NumericUnit<*>): HigherOrderUnit {
        return HigherOrderUnit(
            listOf(rawValue.toMass(MassUnit.KILOGRAM), 1.meters, 1.meters, other),
            listOf(1.seconds.fakeDuration, 1.seconds.fakeDuration)
        )
    }

    override fun times(scalar: Double): PowerDepre {
        return PowerDepre(rawValue * scalar)
    }
    override fun times(scalar: Number): PowerDepre {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): PowerDepre {
        return this / scalar.toDouble()
    }
    override fun div(scalar: Double): PowerDepre {
        return PowerDepre(rawValue / scalar)
    }
}

operator fun Number.times(element: PowerDepre): PowerDepre {
    return element * this.toDouble()
}
fun Int.toPower(units: PowerUnitDepre): PowerDepre {
    return PowerDepre(this * units.scale)
}


fun Long.toPower(units: PowerUnitDepre): PowerDepre {
    return PowerDepre(this * units.scale)
}

fun Double.toPower(units: PowerUnitDepre): PowerDepre {
    return PowerDepre(this * units.scale)
}

inline val Number.watts: PowerDepre
    get() = this.toDouble().toPower(PowerUnitDepre.WATTS)

enum class PowerUnitDepre(override val scale: Double) : FloatUnitScale {
    WATTS(1.0),
    KILOWATTS(1000.0)
}
