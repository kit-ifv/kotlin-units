package units

import kotlin.time.Duration
import kotlin.time.DurationUnit

/**
 * A unit which occurs when the realm of typesafe defined units is left. Represents values with a unit of entire meters,
 * seconds and/or kilograms.
 *
 * __This type does not have the performance guarantees of the kotlin-units library!__
 * If you can stay in the type safe environment. This should only be used when no other options are available.
 *
 * It supports basic operations like {+ - * / ==}
 */
class OutOfBoundsUnit(val rawValue: Double, val unit: PhysicsUnit) {

    operator fun plus (other: OutOfBoundsUnit): OutOfBoundsUnit {
        return OutOfBoundsUnit(rawValue + other.rawValue, unit + other.unit)
    }

    operator fun minus(other: OutOfBoundsUnit): OutOfBoundsUnit {
        return OutOfBoundsUnit(rawValue - other.rawValue, unit - other.unit)
    }

    operator fun times (other: OutOfBoundsUnit): OutOfBoundsUnit {
        return OutOfBoundsUnit(rawValue * other.rawValue, unit * other.unit)
    }

    operator fun div (other: OutOfBoundsUnit): OutOfBoundsUnit {
        return OutOfBoundsUnit(rawValue / other.rawValue, unit / other.unit)
    }

    /**
     * returns true if the units match and the values are less than 1e-9 appart from each other.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is OutOfBoundsUnit) return false
        return rawValue - other.rawValue < 1e-9 && unit == other.unit
    }
}

/**
 * This class represents a unit for handling kinematic operations. Only meters, seconds and kg can be represented.
 * It provides + - * / operations.
 */
class PhysicsUnit(val meter_exponent: Int, val seconds_exponent: Int, val kg_exponent: Int) {
    /**
     * Checks whether 'this' and 'other' are equal, to ensure only sensible operations.
     * @throws IllegalArgumentException if 'this' and 'other' are not equal, since then no sensible behavior can be
     * defined.
     * @return 'this' if both can be sensibly added.
     */
    operator fun plus(other: PhysicsUnit): PhysicsUnit {
        assert(this == other )
        { "Can't add units with different exponents\n" +
                "Unit1: $this\n" +
                "Unit2: $other"
        }
        return this
    }

    /**
     * Checks whether 'this' and 'other' are equal, to ensure only sensible operations.
     * @throws IllegalArgumentException if 'this' and 'other' are not equal, since then no sensible behavior can be
     * defined.
     * @return 'this' if both can be sensibly subtracted.
     */
    operator fun minus(other: PhysicsUnit): PhysicsUnit {
        assert(this == other )
        { "Can't subtract units with different exponents\n" +
                "Unit1: $this\n" +
                "Unit2: $other"
        }
        return this
    }

    /**
     * Adds the exponents together.
     * @return a new PhysicsUnit with subtracted exponents.
     */
    operator fun times (other: PhysicsUnit): PhysicsUnit {
        return PhysicsUnit(
            meter_exponent + other.meter_exponent,
            seconds_exponent + other.seconds_exponent,
            kg_exponent + other.kg_exponent)
    }

    /**
     * @return a new PhysicsUnit with `this` exponents subtracted by `other`'s exponents.
     */
    operator fun div (other: PhysicsUnit): PhysicsUnit {
        return PhysicsUnit(
            meter_exponent - other.meter_exponent,
            seconds_exponent - other.seconds_exponent,
            kg_exponent - other.kg_exponent)
    }


    /**
     * @return true if all exponents are equal. Otherwise, false.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is PhysicsUnit) return false
        return meter_exponent == other.meter_exponent &&
                seconds_exponent == other.seconds_exponent &&
                kg_exponent == other.kg_exponent
    }

    override fun toString(): String {
        return "m^$meter_exponent s^$seconds_exponent kg^$kg_exponent"
    }
}


/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Acceleration.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inMetersPerSecondsSquared,
        PhysicsUnit(1, -2, 0))
}


/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Area.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inSquareMeters,
        PhysicsUnit(2, 0, 0))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Distance.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inMeters,
        PhysicsUnit(1, 0, 0))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Duration.toOutOfBoundsUnit(): OutOfBoundsUnit{
    return OutOfBoundsUnit(
        asSeconds,
        PhysicsUnit(0,1,0))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun SquareDuration.toOutOfBoundsUnit(): OutOfBoundsUnit{
    return OutOfBoundsUnit(
        this.inSquareSeconds,
        PhysicsUnit(0,2,0))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun CubicDuration.toOutOfBoundsUnit(): OutOfBoundsUnit{
    return OutOfBoundsUnit(
        this.inCubicSeconds,
        PhysicsUnit(0,3,0))
}


/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Energy.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inJoule,
        PhysicsUnit(2, -2, 1))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Frequency.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inHertz,
        PhysicsUnit(0, -1, 0))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Mass.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inKilograms,
        PhysicsUnit(0, 0, 1))
}


/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Newton.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inNewton,
        PhysicsUnit(1, -2, 1))
}


/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Power.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inWatts,
        PhysicsUnit(2, -3, 1))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Speed.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inMetersPerSecond,
        PhysicsUnit(1, -1, 0))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Impulse.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inNewtonSeconds,
        PhysicsUnit(1, -1, 1))
}

/**
 * This is likely not what you want to do. This is a function for internal use.
 */
fun Volume.toOutOfBoundsUnit(): OutOfBoundsUnit {
    return OutOfBoundsUnit(inCubicMeters,
        PhysicsUnit(3, 0, 0))
}