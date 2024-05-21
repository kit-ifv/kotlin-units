package units

import kotlin.time.Duration
import kotlin.time.DurationUnit

@JvmInline
value class Speed(override val rawValue: Double) : FloatUnit<SpeedUnit>, ScalarUnit<SpeedUnit> {

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> =
            mapOf(Distance::class.java to 1, DurationImitator::class.java to -1)

        fun parse(text: String): Speed {
            val index = text.indexOfFirst { it !in '0'..'9' && it !in setOf('.')}
            val numericComponent = text.substring(0, index).toDouble()
            val unitComponent = SpeedUnit.parseUnit(text.substring(index))

            return numericComponent.toSpeed(unitComponent)
        }
    }

    override operator fun times(time: Duration): Distance {
        return (this.rawValue * time.toDouble(DurationUnit.SECONDS)).toDistance(DistanceUnit.METERS)
    }


    override fun plus(other: FloatUnit<SpeedUnit>): Speed {
        return Speed(rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<SpeedUnit>): Speed {
        return this + (-other)
    }

    override fun unaryMinus(): Speed {
        return Speed(-rawValue)
    }

    override fun times(scalar: Double): Speed {
        return Speed(rawValue * scalar)
    }

    override fun div(scalar: Double): Speed {
        return Speed(rawValue / scalar)
    }

}

operator fun Number.times(element: Speed): Speed {
    return element * this.toDouble()
}

fun Int.toSpeed(units: SpeedUnit): Speed {
    return Speed(this * units.scale)
}

fun Long.toSpeed(units: SpeedUnit): Speed {
    return Speed(this * units.scale)
}

fun Double.toSpeed(units: SpeedUnit): Speed {
    return Speed(this * units.scale)
}
fun Number.toSpeed(units: SpeedUnit): Speed {
    return toDouble().toSpeed(units)
}
val Number.kmh: Speed
    get() {
        return toSpeed(SpeedUnit.KILOMETER_PER_HOUR)
    }

enum class SpeedUnit(override val scale: Double, val symbol: String) : FloatUnitScale {
    METER_PER_SECOND(1.0, "m/s"),
    KILOMETER_PER_HOUR(0.277778, "km/h"),
    MILES_PER_HOUR(0.44704, "mph"),
    KNOTS(0.514444, "knot");
    companion object {
        fun parseUnit(text: String): SpeedUnit = entries.first {it.symbol == text}
    }

}
