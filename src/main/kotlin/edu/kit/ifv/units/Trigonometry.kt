@file:Suppress("unused")
package edu.kit.ifv.units

import kotlin.math.PI


const val DEGREES = 180 / PI
const val RADIANS = PI / 180
@JvmInline
value class Radians(internal val rawValue: Double): Comparable<Radians> {
    fun toDegrees(): Degrees {
        return Degrees(rawValue * DEGREES)
    }

    fun toDouble(): Double {
        return rawValue
    }

    override fun compareTo(other: Radians): Int {
        return this.rawValue.compareTo(other.rawValue)
    }

}

fun min(a: Radians, b: Radians): Radians {
    if (a < b) return a
    return b
}

fun max(a: Radians, b: Radians): Radians {
    if (a > b) return a
    return b
}

fun Radians.coerceIn(min: Radians, max: Radians): Radians {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Radians.coerceAtLeast(min: Radians): Radians {
    return  max(this, min)
}

fun Radians.coerceAtMost(max: Radians): Radians {
    return min(this, max)
}



@JvmInline
value class Degrees(internal val rawValue: Double): Comparable<Degrees> {
    fun toRadians(): Radians {
        return Radians(rawValue * RADIANS)
    }
    fun toDouble(): Double {
        return rawValue
    }

    override fun compareTo(other: Degrees): Int {
        return this.rawValue.compareTo(other.rawValue)
    }
}

fun min(a: Degrees, b: Degrees): Degrees {
    if (a < b) return a
    return b
}

fun max(a: Degrees, b: Degrees): Degrees {
    if (a > b) return a
    return b
}

fun Degrees.coerceIn(min: Degrees, max: Degrees): Degrees {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Degrees.coerceAtLeast(min: Degrees): Degrees {
    return  max(this, min)
}

fun Degrees.coerceAtMost(max: Degrees): Degrees {
    return min(this, max)
}


val Double.degrees get() = Degrees(this)
val Number.degrees get() = this.toDouble().degrees
val Double.radians get() = Radians(this)
val Number.radians get() = this.toDouble().radians