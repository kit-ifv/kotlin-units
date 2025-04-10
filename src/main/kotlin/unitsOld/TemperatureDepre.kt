package unitsOld

import kotlin.math.roundToLong

@JvmInline
value class TemperatureDepre(override val rawValue: Long) : LongUnit<TempUnitDepre> {
    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(TemperatureDepre::class.java to 1)
    }

    override fun plus(other: LongUnit<TempUnitDepre>): TemperatureDepre {
        return TemperatureDepre(rawValue + other.rawValue)
    }

    override fun unaryMinus(): TemperatureDepre {
        return TemperatureDepre(-rawValue)
    }

    override fun minus(other: LongUnit<TempUnitDepre>): TemperatureDepre {
        return this + (-other)
    }

    override fun toLong(unit: TempUnitDepre): Long {
        return convertUnit(rawValue - unit.offset, 1L, unit.scale)
    }

    override fun toInt(unit: TempUnitDepre): Int {
        return toLong(unit).coerceIn(Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong()).toInt()
    }

    override fun toDouble(unit: TempUnitDepre): Double {
        return convertUnit(rawValue.toDouble() - unit.offset, 1L, unit.scale)


    }
}

enum class TempUnitDepre(override val scale: Long, val offset: Long) : LongUnitScale {

    KELVIN(1_000_000L, 0),
    FAHRENHEIT(555_556L, 255_372_222L),
    CELSIUS(1_000_000L, 273_150_000L);


}

fun Int.toTemperature(unit: TempUnitDepre): TemperatureDepre {
    return TemperatureDepre(convertUnit(toLong(), unit.scale) + unit.offset)
}

fun Long.toTemperature(unit: TempUnitDepre): TemperatureDepre {
    return TemperatureDepre(convertUnit(this, unit.scale) + unit.offset)
}

fun Double.toTemperature(unit: TempUnitDepre): TemperatureDepre {
    val roundToLong = (this * unit.scale).roundToLong()
    return TemperatureDepre(roundToLong + unit.offset)
}
