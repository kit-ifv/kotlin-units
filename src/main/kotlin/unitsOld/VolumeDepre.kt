package unitsOld

@JvmInline
value class VolumeDepre internal constructor(override val rawValue: Double) : FloatUnit<VolumeUnit>, ScalarUnit<VolumeUnit> {
    companion object {
        /**
         * Create the area defined by two distances spanning a rectangle
         */
        fun ofCube(a: DistanceDepre, b: DistanceDepre, c: DistanceDepre): VolumeDepre {
            return VolumeDepre(a.toDouble(DistanceUnitDepre.METERS) * b.toDouble(DistanceUnitDepre.METERS) * c.toDouble(DistanceUnitDepre.METERS))
        }

        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(DistanceDepre::class.java to 3)
    }

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    override fun plus(other: FloatUnit<VolumeUnit>): VolumeDepre {
        return VolumeDepre(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<VolumeUnit>): VolumeDepre {
        return this + (-other)
    }

    override fun unaryMinus(): VolumeDepre {
        return VolumeDepre(-this.rawValue)
    }

    val benzene: EnergyDepre get() = this.toDouble(VolumeUnit.LITER).toEnergy(EnergyUnit.BENZENE_EQUIVALENT)

    /**
     * Since Area is a representation of a higher order type that can be specified we need to override the generic
     * multiplication rules to accommodate the fact that we have factually two distances to specify an area
     */
    override operator fun times(other: NumericUnit<*>): HigherOrderUnit {
        return HigherOrderUnit(rawValue.toDistance(DistanceUnitDepre.atomic()), 1.toDistance(DistanceUnitDepre.atomic()), other)
    }

    override operator fun div(other: NumericUnit<*>): HigherOrderUnit {
        return HigherOrderUnit(
            listOf(rawValue.toDistance(DistanceUnitDepre.atomic()), 1.toDistance(DistanceUnitDepre.atomic())),
            listOf(other)
        )
    }

    operator fun div(other: DistanceDepre): AreaDepre {
        return AreaDepre(rawValue / other.toDouble(DistanceUnitDepre.METERS))
    }

    override fun times(scalar: Double): VolumeDepre {
        return VolumeDepre(rawValue * scalar)
    }
    override fun times(scalar: Number): VolumeDepre {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): VolumeDepre {
        return this / scalar.toDouble()
    }
    override fun div(scalar: Double): VolumeDepre {
        return VolumeDepre(rawValue / scalar)
    }

}
operator fun Number.times(element: VolumeDepre): VolumeDepre {
    return element * this.toDouble()
}

fun Int.toVolume(units: VolumeUnit): VolumeDepre {
    return VolumeDepre(this * units.scale)
}

fun Long.toVolume(units: VolumeUnit): VolumeDepre {
    return VolumeDepre(this * units.scale)
}

fun Double.toVolume(units: VolumeUnit): VolumeDepre {
    return VolumeDepre(this * units.scale)
}

fun Number.toVolume(units: VolumeUnit): VolumeDepre {
    return toDouble().toVolume(units)
}

val Number.liters: VolumeDepre get() = toVolume(VolumeUnit.LITER)
val Number.cubicMeters: VolumeDepre get() = toVolume(VolumeUnit.CUBIC_METER)

enum class VolumeUnit(override val scale: Double) : FloatUnitScale {
    CUBIC_METER(1.0),
    LITER(0.001),

}
