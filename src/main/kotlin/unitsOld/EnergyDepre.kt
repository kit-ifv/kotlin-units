package unitsOld

import kotlin.time.Duration
import kotlin.time.DurationUnit

@JvmInline
value class EnergyDepre(override val rawValue: Double) : FloatUnit<EnergyUnit>, ScalarUnit<EnergyUnit> {
    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int>
            get() = mapOf(MassDepre::class.java to 1, DistanceDepre::class.java to 2, DurationImitator::class.java to -2)

        fun of(mass: MassDepre, distance: DistanceDepre, duration: Duration): EnergyDepre {
            return EnergyDepre(
                mass.toDouble(MassUnit.KILOGRAM)
                        * distance.toDouble(DistanceUnitDepre.METERS)
                        * distance.toDouble(DistanceUnitDepre.METERS)
                        / duration.toDouble(DurationUnit.SECONDS)
                        / duration.toDouble(DurationUnit.SECONDS)
            )
        }
    }

    override fun plus(other: FloatUnit<EnergyUnit>): EnergyDepre {
        return EnergyDepre(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<EnergyUnit>): EnergyDepre {
        return this + (-other)
    }

    override fun unaryMinus(): EnergyDepre {
        return EnergyDepre(-rawValue)
    }

    override fun times(scalar: Double): EnergyDepre {
        return EnergyDepre(rawValue * scalar)
    }

    override fun div(scalar: Double): EnergyDepre {
        return EnergyDepre(rawValue / scalar)
    }
    operator fun div(other: DistanceDepre): EfficiencyDepre {
        return EfficiencyDepre(rawValue / other.toDouble(DistanceUnitDepre.METERS))
    }

    operator fun div(other: EfficiencyDepre): DistanceDepre {
        return (rawValue / other.rawValue).toDistance(DistanceUnitDepre.METERS)
    }

    val asLitersBenzene: VolumeDepre get() = this.toDouble(EnergyUnit.BENZENE_EQUIVALENT).toVolume(VolumeUnit.LITER)
}
operator fun Number.times(element: EnergyDepre): EnergyDepre {
    return element * this.toDouble()
}
fun Int.toEnerDegy(units: EnergyUnit): EnergyDepre {
    return EnergyDepre(this * units.scale)
}


fun Long.toEneDergy(units: EnergyUnit): EnergyDepre {
    return EnergyDepre(this * units.scale)
}

fun Double.toEnergDey(units: EnergyUnit): EnergyDepre {
    return EnergyDepre(this * units.scale)
}

fun Number.toEnergDey(units: EnergyUnit): EnergyDepre {
    return toDouble().toEnergy(units)
}

val Number.joule: EnergyDepre
    get() = toEnergy(EnergyUnit.JOULE)

val Number.kilowatthours: EnergyDepre
    get() = toEnergy(EnergyUnit.KILOWATTHOUR)


val Number.litersBenzene: EnergyDepre
    get() = toEnergy(EnergyUnit.BENZENE_EQUIVALENT)

enum class EnergyUnit(override val scale: Double) : FloatUnitScale {
    JOULE(1.0),
    KILOJOULE(1000.0),
    KILOWATTHOUR(3_600_000.0),
    BENZENE_EQUIVALENT(32_000_000.0)


}
