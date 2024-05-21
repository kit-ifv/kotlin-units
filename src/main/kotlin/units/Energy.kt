package units

import kotlin.time.Duration
import kotlin.time.DurationUnit

@JvmInline
value class Energy(override val rawValue: Double) : FloatUnit<EnergyUnit>, ScalarUnit<EnergyUnit> {
    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int>
            get() = mapOf(Mass::class.java to 1, Distance::class.java to 2, DurationImitator::class.java to -2)

        fun of(mass: Mass, distance: Distance, duration: Duration): Energy {
            return Energy(
                mass.toDouble(MassUnit.KILOGRAM)
                        * distance.toDouble(DistanceUnit.METERS)
                        * distance.toDouble(DistanceUnit.METERS)
                        / duration.toDouble(DurationUnit.SECONDS)
                        / duration.toDouble(DurationUnit.SECONDS)
            )
        }
    }

    override fun plus(other: FloatUnit<EnergyUnit>): Energy {
        return Energy(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<EnergyUnit>): Energy {
        return this + (-other)
    }

    override fun unaryMinus(): Energy {
        return Energy(-rawValue)
    }

    override fun times(scalar: Double): Energy {
        return Energy(rawValue * scalar)
    }
    override fun div(scalar: Double): Energy {
        return Energy(rawValue / scalar)
    }
    operator fun div(other: Distance): Efficiency {
        return Efficiency(rawValue / other.toDouble(DistanceUnit.METERS))
    }

    operator fun div(other: Efficiency): Distance {
        return (rawValue / other.rawValue).toDistance(DistanceUnit.METERS)
    }

    val asLitersBenzene: Volume get() = this.toDouble(EnergyUnit.BENZENE_EQUIVALENT).toVolume(VolumeUnit.LITER)
}
operator fun Number.times(element: Energy): Energy {
    return element * this.toDouble()
}
fun Int.toEnergy(units: EnergyUnit): Energy {
    return Energy(this * units.scale)
}


fun Long.toEnergy(units: EnergyUnit): Energy {
    return Energy(this * units.scale)
}

fun Double.toEnergy(units: EnergyUnit): Energy {
    return Energy(this * units.scale)
}

fun Number.toEnergy(units: EnergyUnit): Energy {
    return toDouble().toEnergy(units)
}

val Number.joule: Energy
    get() = toEnergy(EnergyUnit.JOULE)

val Number.kilowatthours: Energy
    get() = toEnergy(EnergyUnit.KILOWATTHOUR)


val Number.litersBenzene: Energy
    get() = toEnergy(EnergyUnit.BENZENE_EQUIVALENT)

enum class EnergyUnit(override val scale: Double) : FloatUnitScale {
    JOULE(1.0),
    KILOJOULE(1000.0),
    KILOWATTHOUR(3_600_000.0),
    BENZENE_EQUIVALENT(32_000_000.0)


}
