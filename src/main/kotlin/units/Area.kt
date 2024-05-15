package units

@JvmInline
value class Area internal constructor(override val rawValue: Double) : FloatUnit<AreaUnit> {
    companion object {
        /**
         * Create the area defined by two distances spanning a rectangle
         */
        fun ofRectangle(a: Distance, b: Distance): Area {
            return Area(a.toDouble(DistanceUnit.METERS) * b.toDouble(DistanceUnit.METERS))
        }

        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(Distance::class.java to 2)
    }

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    override fun plus(other: FloatUnit<AreaUnit>): Area {
        return Area(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<AreaUnit>): Area {
        return this + (-other)
    }

    override fun unaryMinus(): Area {
        return Area(-this.rawValue)
    }
    operator fun times(distance: Distance): Volume {
        return Volume(rawValue * distance.toDouble(DistanceUnit.METERS))
    }
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

    operator fun div(other: Distance): Distance {
        return Distance((rawValue / other.rawValue).toLong())
    }

}

fun Int.toArea(units: AreaUnit): Area {
    return Area(this * units.scale)
}

fun Long.toArea(units: AreaUnit): Area {
    return Area(this * units.scale)
}

fun Double.toArea(units: AreaUnit): Area {
    return Area(this * units.scale)
}

enum class AreaUnit(override val scale: Double) : FloatUnitScale {
    SQUARE_METERS(1.0),
    SQUARE_INCH(0.00064516),
    SQUARE_KILOMETERS(1000.0 * 1000.0)
}
