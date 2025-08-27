@file:Suppress("unused")
package edu.kit.ifv.units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@JvmInline
value class Impulse internal constructor(val rawValue: Double) : Comparable<Impulse>, FlexibleUnit {
    operator fun plus(other: Impulse) = Impulse(rawValue + other.rawValue)

    operator fun times(scalar: Double) = Impulse(rawValue * scalar)
    operator fun times(scalar: Float) = Impulse(rawValue * scalar)
    operator fun times(scalar: Int) = Impulse((rawValue * scalar))
    operator fun times(scalar: Long)  = Impulse((rawValue * scalar))

    operator fun div(scalar: Double): Impulse = Impulse(rawValue / scalar)
    operator fun div(scalar: Float): Impulse = Impulse(rawValue / scalar)
    operator fun div(scalar: Int): Impulse =  Impulse((rawValue / scalar))
    operator fun div(scalar: Long): Impulse = Impulse((rawValue / scalar))

    operator fun rangeTo(other: Impulse): ClosedImpulseRange = ClosedImpulseRange(this, other)

    operator fun rangeUntil(other: Impulse) = OpenImpulseRange(this, other)
    override fun compareTo(other: Impulse): Int = rawValue.compareTo(other.rawValue)


    //--- Define conversions to "naked" number representations here.

    inline val inNewtonSeconds: Double get() = rawValue / Newton_Seconds

    //--- Define different operations below:
    operator fun times(acceleration: Acceleration): Power
        = (inNewtonSeconds * acceleration.inMetersPerSecondsSquared).watts
    operator fun times(frequency: Frequency): Force
        = (inNewtonSeconds * frequency.inHertz).newton
    operator fun times(speed: Speed): Energy
        = (inNewtonSeconds * speed.inMetersPerSecond).joule


    operator fun div(other: Impulse): Double = rawValue / other.rawValue
    operator fun div(speed: Speed): Mass = (inNewtonSeconds / speed.inMetersPerSecond).kilograms
    operator fun div(mass: Mass): Speed = (inNewtonSeconds / mass.inKilograms).metersPerSecond
    operator fun div(duration: Duration): Force = (inNewtonSeconds / duration.asSeconds).newton
    operator fun div(force: Force): Duration = (inNewtonSeconds / force.inNewton).seconds


    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inNewtonSeconds, PhysicsUnit(1,-1,1))
    }

    companion object {
        val MAX = Impulse(Double.MAX_VALUE)
        val ZERO = Impulse(.0)
        val Newton_Seconds = 1.0
    }
}

class ClosedImpulseRange(override val start: Impulse, override val endInclusive: Impulse) : ClosedRange<Impulse> {
    override fun contains(value: Impulse): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenImpulseRange(override val start: Impulse, override val endExclusive: Impulse) : OpenEndRange<Impulse> {
    override fun contains(value: Impulse): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}


fun Long.toImpulse(unit: ImpulseUnit): Impulse {
    return Impulse(this * unit.scale)
}

fun Double.toImpulse(unit: ImpulseUnit): Impulse {
    return Impulse(this * unit.scale)
}

fun Int.toImpulse(unit: ImpulseUnit): Impulse {
    return Impulse(this * unit.scale)
}

fun Float.toImpulse(unit: ImpulseUnit): Impulse {
    return Impulse(this * unit.scale)
}
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfImpulse")
fun <T> Iterable<T>.sumOf(selector: (T) -> Impulse): Impulse {
    var sum = 0.toImpulse(ImpulseUnit.DEFAULT)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Iterable<Impulse>.min() = minBy { it }
fun Iterable<Impulse>.max() = maxBy { it }
fun Iterable<Impulse>.average(): Impulse {
    var sum = 0.toImpulse(ImpulseUnit.DEFAULT)
    var count = 0
    for (element in this) {
        sum += element
        count++
    }
    return sum / count
}

fun abs(element: Impulse) = Impulse(element.rawValue.absoluteValue)


enum class ImpulseUnit(val scale: Double)  {
    DEFAULT(1.0),
}

fun min(a: Impulse, b: Impulse): Impulse {
    if (a < b) return a
    return b
}

fun max(a: Impulse, b: Impulse): Impulse {
    if (a > b) return a
    return b
}

fun Impulse.coerceIn(min: Impulse, max: Impulse): Impulse {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Impulse.coerceAtLeast(min: Impulse): Impulse {
    return  max(this, min)
}

fun Impulse.coerceAtMost(max: Impulse): Impulse {
    return min(this, max)
}