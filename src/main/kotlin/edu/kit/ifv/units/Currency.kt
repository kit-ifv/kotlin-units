package edu.kit.ifv.units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue

@JvmInline
value class Currency internal constructor(val rawValue: Double): Comparable<Currency> {

    operator fun unaryMinus(): Currency = Currency(-rawValue)
    operator fun plus(other: Currency) = Currency(rawValue + other.rawValue)
    operator fun minus(other: Currency) = Currency(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Currency(rawValue * scalar)
    operator fun times(scalar: Float) = Currency(rawValue * scalar)
    operator fun times(scalar: Int) = Currency((rawValue * scalar))
    operator fun times(scalar: Long)  = Currency((rawValue * scalar))

    operator fun div(scalar: Double): Currency = Currency(rawValue / scalar)
    operator fun div(scalar: Float): Currency = Currency(rawValue / scalar)
    operator fun div(scalar: Int): Currency =  Currency((rawValue / scalar))
    operator fun div(scalar: Long): Currency = Currency((rawValue / scalar))

    operator fun rangeTo(other: Currency): ClosedCurrencyRange = ClosedCurrencyRange(this, other)

    operator fun rangeUntil(other: Currency) =OpenCurrencyRange(this, other)

    operator fun rem(other: Currency): Currency = Currency((rawValue % other.rawValue))
    override fun compareTo(other: Currency): Int = rawValue.compareTo(other.rawValue)

    fun toInt(unit: CurrencyUnit): Int = (rawValue / unit.scale).toInt()
    fun toLong(unit: CurrencyUnit): Long = (rawValue / unit.scale).toLong()
    fun toDouble(unit: CurrencyUnit): Double = rawValue / unit.scale
    //--- Define conversions to "naked" number representations here.

    val inEuros: Double get() = rawValue / EUROS

    //--- Define different operations below:

    operator fun div(other: Currency): Double = rawValue / other.rawValue
    companion object {
        val MAX = Currency(Double.MAX_VALUE)
        val ZERO = Currency(.0)
        const val EUROS: Double = 1.0
    }
}

class ClosedCurrencyRange(override val start: Currency, override val endInclusive: Currency): ClosedRange<Currency> {
    override fun contains(value: Currency): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenCurrencyRange(override val start: Currency, override val endExclusive: Currency): OpenEndRange<Currency> {
    override fun contains(value: Currency): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}



fun Long.toCurrency(unit: CurrencyUnit): Currency {
    return Currency(this * unit.scale)
}
fun Double.toCurrency(unit: CurrencyUnit): Currency {
    return Currency(this * unit.scale)
}
fun Int.toCurrency(unit: CurrencyUnit): Currency {
    return Currency(this * unit.scale)
}
fun Float.toCurrency(unit: CurrencyUnit): Currency {
    return Currency(this * unit.scale)
}




@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfCurrency")
fun <T> Iterable<T>.sumOf(selector: (T) -> Currency): Currency {
    var sum = 0.euros
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Currency>.min() = minBy { it }
fun Iterable<Currency>.max() = maxBy { it }
fun Iterable<Currency>.average(): Currency {
    var sum = 0.euros
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}
fun abs(element: Currency) = Currency(element.rawValue.absoluteValue)

enum class CurrencyUnit(val scale: Double) {
    EUROS(Currency.EUROS),
}

fun min(a: Currency, b: Currency): Currency {
    if (a < b) return a
    return b
}

fun max(a: Currency, b: Currency): Currency {
    if (a > b) return a
    return b
}

fun Currency.coerceIn(min: Currency, max: Currency): Currency {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Currency.coerceAtLeast(min: Currency): Currency {
    return  max(this, min)
}

fun Currency.coerceAtMost(max: Currency): Currency {
    return min(this, max)
}