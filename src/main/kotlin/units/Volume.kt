package units

@JvmInline
value class Volume internal constructor(override val rawValue: Double) : FloatUnit<VolumeUnit>, ScalarUnit<VolumeUnit> {
    companion object {
        /**
         * Create the area defined by two distances spanning a rectangle
         */
        fun ofCube(a: Distance, b: Distance, c: Distance): Volume {
            return Volume(a.toDouble(DistanceUnit.METERS) * b.toDouble(DistanceUnit.METERS) * c.toDouble(DistanceUnit.METERS))
        }

        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(Distance::class.java to 3)
    }

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    override fun plus(other: FloatUnit<VolumeUnit>): Volume {
        return Volume(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<VolumeUnit>): Volume {
        return this + (-other)
    }

    override fun unaryMinus(): Volume {
        return Volume(-this.rawValue)
    }

    val benzene: Energy get() = this.toDouble(VolumeUnit.LITER).toEnergy(EnergyUnit.BENZENE_EQUIVALENT)

    /**
     * Since Area is a representation of a higher order type that can be specified we need to override the generic
     * multiplication rules to accommodate the fact that we have factually two distances to specify an area
     */
    override operator fun times(other: NumericUnit<*>): HigherOrderUnit {
        return HigherOrderUnit(rawValue.toDistance(DistanceUnit.atomic()), 1.toDistance(DistanceUnit.atomic()), other)
    }

    override operator fun div(other: NumericUnit<*>): HigherOrderUnit {
        return HigherOrderUnit(
            listOf(rawValue.toDistance(DistanceUnit.atomic()), 1.toDistance(DistanceUnit.atomic())),
            listOf(other)
        )
    }

    operator fun div(other: Distance): Area {
        return Area(rawValue / other.toDouble(DistanceUnit.METERS))
    }

    override fun times(scalar: Double): Volume {
        return Volume(rawValue * scalar)
    }
    override fun times(scalar: Number): Volume {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): Volume {
        return this / scalar.toDouble()
    }
    override fun div(scalar: Double): Volume {
        return Volume(rawValue / scalar)
    }

}
operator fun Number.times(element: Volume): Volume {
    return element * this.toDouble()
}

fun Int.toVolume(units: VolumeUnit): Volume {
    return Volume(this * units.scale)
}

fun Long.toVolume(units: VolumeUnit): Volume {
    return Volume(this * units.scale)
}

fun Double.toVolume(units: VolumeUnit): Volume {
    return Volume(this * units.scale)
}

fun Number.toVolume(units: VolumeUnit): Volume {
    return toDouble().toVolume(units)
}

val Number.liters: Volume get() = toVolume(VolumeUnit.LITER)
val Number.cubicMeters: Volume get() = toVolume(VolumeUnit.CUBIC_METER)

enum class VolumeUnit(override val scale: Double) : FloatUnitScale {
    CUBIC_METER(1.0),
    LITER(0.001),

}
