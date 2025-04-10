package unitsOld

@JvmInline
value class AreaDepre internal constructor(override val rawValue: Double) : FloatUnit<AreaUnitDepre>, ScalarUnit<AreaUnitDepre> {
    companion object {
        /**
         * Create the area defined by two distances spanning a rectangle
         */
        fun ofRectangle(a: DistanceDepre, b: DistanceDepre): AreaDepre {
            return AreaDepre(a.toDouble(DistanceUnitDepre.METERS) * b.toDouble(DistanceUnitDepre.METERS))
        }

        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(DistanceDepre::class.java to 2)
    }

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    override fun plus(other: FloatUnit<AreaUnitDepre>): AreaDepre {
        return AreaDepre(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<AreaUnitDepre>): AreaDepre {
        return this + (-other)
    }

    override fun unaryMinus(): AreaDepre {
        return AreaDepre(-this.rawValue)
    }
    operator fun times(distance: DistanceDepre): VolumeDepre {
        return VolumeDepre(rawValue * distance.toDouble(DistanceUnitDepre.METERS))
    }
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
    override fun times(scalar: Double): AreaDepre {
        return AreaDepre(rawValue  * scalar)
    }


    override fun div(scalar: Double): AreaDepre {
        return AreaDepre(rawValue  / scalar)
    }

    operator fun div(other: DistanceDepre): DistanceDepre {
        return DistanceDepre((rawValue / other.rawValue).toLong())
    }

}

operator fun Number.times(areaDepre: AreaDepre): AreaDepre {
    return areaDepre * this.toDouble()
}

fun Int.toArea(units: AreaUnitDepre): AreaDepre {
    return AreaDepre(this * units.scale)
}

fun Long.toArea(units: AreaUnitDepre): AreaDepre {
    return AreaDepre(this * units.scale)
}

fun Double.toArea(units: AreaUnitDepre): AreaDepre {
    return AreaDepre(this * units.scale)
}

enum class AreaUnitDepre(override val scale: Double) : FloatUnitScale {
    SQUARE_METERS(1.0),
    SQUARE_INCH(0.00064516),
    SQUARE_KILOMETERS(1000.0 * 1000.0)
}
