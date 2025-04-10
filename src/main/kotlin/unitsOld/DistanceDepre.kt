package unitsOld

import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.DurationUnit

@JvmInline
value class DistanceDepre(override val rawValue: Long) : LongUnit<DistanceUnitDepre>,  ConcreteScalarUnit<DistanceDepre, DistanceUnitDepre> {

    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    val inWholeMillimeters: Long
        get() = toLong(DistanceUnitDepre.MILLIMETERS)

    val inWholeCentimeters: Long
        get() = toLong(DistanceUnitDepre.CENTIMETERS)

    val inWholeMeters: Long
        get() = toLong(DistanceUnitDepre.METERS)

    companion object {
        val INFINITE = DistanceDepre(Long.MAX_VALUE)

        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(DistanceDepre::class.java to 1)


        fun parse(text: String): DistanceDepre {
            val index = text.indexOfFirst { it !in '0'..'9' && it !in setOf('.')}
            val numericComponent = text.substring(0, index).toDouble()
            val unitComponent = DistanceUnitDepre.parseUnit(text.substring(index))

            return numericComponent.toDistance(unitComponent)
        }

        fun ofMeters(i: Int): DistanceDepre {
            return i.toDistance(DistanceUnitDepre.METERS)
        }

        fun ofKilometers(i: Int): DistanceDepre {
            return i.toDistance(DistanceUnitDepre.KILOMETERS)
        }

        fun ofKilometers(d: Double): DistanceDepre {
            return d.toDistance(DistanceUnitDepre.KILOMETERS)
        }

    }

    override fun minus(other: LongUnit<DistanceUnitDepre>): DistanceDepre {
        return this + (-other)
    }

    override fun unaryMinus(): DistanceDepre {
        return DistanceDepre(-rawValue)
    }



    override fun times(scalar: Double): DistanceDepre {
        return DistanceDepre((scalar * rawValue).roundToLong())
    }
    override fun times(scalar: Number): DistanceDepre {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): DistanceDepre {
        return this / scalar.toDouble()
    }

    override fun div(scalar: Double): DistanceDepre {
        return DistanceDepre((rawValue / scalar).roundToLong())
    }

//    override fun minus(other: LongUnit<DistanceUnit>): Distance {
//        return Distance(rawValue + other.rawValue)
//    }

    override fun plus(other: LongUnit<DistanceUnitDepre>): DistanceDepre {
        return DistanceDepre(rawValue + other.rawValue)
    }

    operator fun times(other: DistanceDepre): AreaDepre {
        return AreaDepre.ofRectangle(this, other)
    }

    override operator fun div(t: Duration): SpeedDepre {
        return (this.toDouble(DistanceUnitDepre.METERS)
                / t.toDouble(DurationUnit.SECONDS)).toSpeed(SpeedUnitDpre.METER_PER_SECOND)
    }


}
enum class DistanceUnitDepre(override val scale: Long, val symbol: String) : LongUnitScale {
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
        fun atomic(): DistanceUnitDepre {
            val atomic = MICROMETERS
            assert(atomic.scale == 1L)
            return atomic
        }

        fun parseUnit(text: String): DistanceUnitDepre = entries.first {it.symbol == text}
    }
}

/**
 *
 * If the need for smaller representation than [DistanceUnitDepre.MICROMETERS] ever arises and different range
 * representations as in the [kotlin.time.Duration] are necessary this method is the entry point for all
 * conversions to allow simple alteration
 */
fun Double.toDistance(unit: DistanceUnitDepre): DistanceDepre {
    val millis = (this * unit.scale).roundToLong()
    return DistanceDepre(convertUnit(millis, 1L))
}

fun Long.toDistance(unit: DistanceUnitDepre): DistanceDepre {
    return DistanceDepre(convertUnit(this, unit.scale))
}

fun Int.toDistance(unit: DistanceUnitDepre): DistanceDepre {
    return toLong().toDistance(unit)
}

inline val Int.meters: DistanceDepre
    get() = this.toDistance(DistanceUnitDepre.METERS)

inline val Int.kilometers: DistanceDepre
    get() = this.toDistance(DistanceUnitDepre.KILOMETERS)


inline val Long.meters: DistanceDepre
    get() = this.toDistance(DistanceUnitDepre.METERS)

inline val Long.kilometers: DistanceDepre
    get() = this.toDistance(DistanceUnitDepre.KILOMETERS)


inline val Double.meters: DistanceDepre
    get() = this.toDistance(DistanceUnitDepre.METERS)

inline val Double.kilometers: DistanceDepre
    get() = this.toDistance(DistanceUnitDepre.KILOMETERS)

inline val Number.meters: DistanceDepre
    get() = this.toDouble().toDistance(DistanceUnitDepre.METERS)

fun abs(distance: DistanceDepre): DistanceDepre {
    val target = distance.rawValue.let { if(it >= 0) it else -it }
    return DistanceDepre(target)
}
