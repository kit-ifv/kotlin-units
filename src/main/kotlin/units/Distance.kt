package units

import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.DurationUnit

@JvmInline
value class Distance(override val rawValue: Long) : LongUnit<DistanceUnit>,  ConcreteScalarUnit<Distance, DistanceUnit> {

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    val inWholeMillimeters: Long
        get() = toLong(DistanceUnit.MILLIMETERS)

    val inWholeCentimeters: Long
        get() = toLong(DistanceUnit.CENTIMETERS)

    val inWholeMeters: Long
        get() = toLong(DistanceUnit.METERS)

    companion object {
        val INFINITE = Distance(Long.MAX_VALUE)

        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(Distance::class.java to 1)


        fun parse(text: String): Distance {
            val index = text.indexOfFirst { it !in '0'..'9' && it !in setOf('.')}
            val numericComponent = text.substring(0, index).toDouble()
            val unitComponent = DistanceUnit.parseUnit(text.substring(index))

            return numericComponent.toDistance(unitComponent)
        }

        fun ofMeters(i: Int): Distance {
            return i.toDistance(DistanceUnit.METERS)
        }

        fun ofKilometers(i: Int): Distance {
            return i.toDistance(DistanceUnit.KILOMETERS)
        }

        fun ofKilometers(d: Double): Distance {
            return d.toDistance(DistanceUnit.KILOMETERS)
        }

    }

    override fun minus(other: LongUnit<DistanceUnit>): Distance {
        return this + (-other)
    }

    override fun unaryMinus(): Distance {
        return Distance(-rawValue)
    }



    override fun times(scalar: Double): Distance {
        return Distance((scalar * rawValue).roundToLong())
    }
    override fun times(scalar: Number): Distance {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): Distance {
        return this / scalar.toDouble()
    }

    override fun div(scalar: Double): Distance {
        return Distance((rawValue / scalar).roundToLong())
    }

//    override fun minus(other: LongUnit<DistanceUnit>): Distance {
//        return Distance(rawValue + other.rawValue)
//    }

    override fun plus(other: LongUnit<DistanceUnit>): Distance {
        return Distance(rawValue + other.rawValue)
    }

    operator fun times(other: Distance): Area {
        return Area.ofRectangle(this, other)
    }

    override operator fun div(t: Duration): Speed {
        return (this.toDouble(DistanceUnit.METERS)
                / t.toDouble(DurationUnit.SECONDS)).toSpeed(SpeedUnit.METER_PER_SECOND)
    }


}
enum class DistanceUnit(override val scale: Long, val symbol: String) : LongUnitScale {
    MICROMETERS(1L, "µm"),
    MILLIMETERS(1000L,"mm"),
    CENTIMETERS(10_000L, "cm"),
    INCH(25_400L, "\""),
    DECIMETERS(100_000L, "dm"),
    YARD(914_400L, "yd"),
    METERS(1_000_000L, "m"),
    KILOMETERS(1_000_000_000L,"km"),
    MILE(1_609_340_000L, "mi"),
    SEA_MILE(1_852_000_000L, "nmi");

    companion object {
        /**
         * The smallest possible unit of distance represented by this enumeration
         */
        fun atomic(): DistanceUnit {
            val atomic = MICROMETERS
            assert(atomic.scale == 1L)
            return atomic
        }

        fun parseUnit(text: String): DistanceUnit = entries.first {it.symbol == text}
    }
}

/**
 *
 * If the need for smaller representation than [DistanceUnit.MICROMETERS] ever arises and different range
 * representations as in the [kotlin.time.Duration] are necessary this method is the entry point for all
 * conversions to allow simple alteration
 */
fun Double.toDistance(unit: DistanceUnit): Distance {
    val millis = (this * unit.scale).roundToLong()
    return Distance(convertUnit(millis, 1L))
}

fun Long.toDistance(unit: DistanceUnit): Distance {
    return Distance(convertUnit(this, unit.scale))
}

fun Int.toDistance(unit: DistanceUnit): Distance {
    return toLong().toDistance(unit)
}

inline val Int.meters: Distance
    get() = this.toDistance(DistanceUnit.METERS)

inline val Int.kilometers: Distance
    get() = this.toDistance(DistanceUnit.KILOMETERS)


inline val Long.meters: Distance
    get() = this.toDistance(DistanceUnit.METERS)

inline val Long.kilometers: Distance
    get() = this.toDistance(DistanceUnit.KILOMETERS)


inline val Double.meters: Distance
    get() = this.toDistance(DistanceUnit.METERS)

inline val Double.kilometers: Distance
    get() = this.toDistance(DistanceUnit.KILOMETERS)

inline val Number.meters: Distance
    get() = this.toDouble().toDistance(DistanceUnit.METERS)

fun abs(distance: Distance): Distance {
    val target = distance.rawValue.let { if(it >= 0) it else -it }
    return Distance(target)
}
