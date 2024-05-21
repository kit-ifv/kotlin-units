package units

import kotlin.math.PI


const val DEGREES = 180 / PI
const val RADIANS = PI / 180
@JvmInline
value class Radians(internal val rawValue: Double) {
    fun toDegrees(): Degrees {
        return Degrees(rawValue * DEGREES)
    }

    fun toDouble(): Double {
        return rawValue
    }

}

@JvmInline
value class Degrees(internal val rawValue: Double) {
    fun toRadians(): Radians {
        return Radians(rawValue * RADIANS)
    }
    fun toDouble(): Double {
        return rawValue
    }
}

val Double.degrees get() = Degrees(this)
val Number.degrees get() = this.toDouble().degrees
val Double.radians get() = Radians(this)
val Number.radians get() = this.toDouble().radians