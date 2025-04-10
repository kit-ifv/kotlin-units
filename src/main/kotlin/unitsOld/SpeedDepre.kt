package unitsOld

import kotlin.time.Duration
import kotlin.time.DurationUnit

@JvmInline
value class SpeedDepre(override val rawValue: Double) : FloatUnit<SpeedUnitDpre>, ScalarUnit<SpeedUnitDpre> {

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> =
            mapOf(DistanceDepre::class.java to 1, DurationImitator::class.java to -1)

        fun parse(text: String): SpeedDepre {
            val index = text.indexOfFirst { it !in '0'..'9' && it !in setOf('.')}
            val numericComponent = text.substring(0, index).toDouble()
            val unitComponent = SpeedUnitDpre.parseUnit(text.substring(index))

            return numericComponent.toSpeed(unitComponent)
        }
    }

    override operator fun times(other: Duration): DistanceDepre {
        return (this.rawValue * other.toDouble(DurationUnit.SECONDS)).toDistance(DistanceUnitDepre.METERS)
    }


    override fun plus(other: FloatUnit<SpeedUnitDpre>): SpeedDepre {
        return SpeedDepre(rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<SpeedUnitDpre>): SpeedDepre {
        return this + (-other)
    }

    override fun unaryMinus(): SpeedDepre {
        return SpeedDepre(-rawValue)
    }

    override fun times(scalar: Double): SpeedDepre {
        return SpeedDepre(rawValue * scalar)
    }
    override fun times(scalar: Number): SpeedDepre {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): SpeedDepre {
        return this / scalar.toDouble()
    }
    override fun div(scalar: Double): SpeedDepre {
        return SpeedDepre(rawValue / scalar)
    }

}

operator fun Number.times(element: SpeedDepre): SpeedDepre {
    return element * this.toDouble()
}

fun Int.toSpeed(units: SpeedUnitDpre): SpeedDepre {
    return SpeedDepre(this * units.scale)
}

fun Long.toSpeed(units: SpeedUnitDpre): SpeedDepre {
    return SpeedDepre(this * units.scale)
}

fun Double.toSpeed(units: SpeedUnitDpre): SpeedDepre {
    return SpeedDepre(this * units.scale)
}
fun Number.toSpeed(units: SpeedUnitDpre): SpeedDepre {
    return toDouble().toSpeed(units)
}
val Number.kmh: SpeedDepre
    get() {
        return toSpeed(SpeedUnitDpre.KILOMETER_PER_HOUR)
    }

enum class SpeedUnitDpre(override val scale: Double, val symbol: String) : FloatUnitScale {
    METER_PER_SECOND(1.0, "m/s"),
    KILOMETER_PER_HOUR(0.277778, "km/h"),
    MILES_PER_HOUR(0.44704, "mph"),
    KNOTS(0.514444, "knot");
    companion object {
        fun parseUnit(text: String): SpeedUnitDpre = entries.first {it.symbol == text}
    }

}
