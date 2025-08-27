package edu.kit.ifv.units

import kotlin.math.absoluteValue


/**
 * This interface defines default functions each type needs to support, to ensure all units can be multiplied together.
 * The mechanism hinges on `OutOfBoundsUnit`, therefore the typesafe realm is left when these functions come into play.
 */
interface FlexibleUnit {

    /**
     * This should only be used manually, if the base units lack some functionality, because calculations with
     * `OutOfBoundsUnit`s __do not__ have the performance guarantees of this library.
     *
     * This method is mostly used for internal conversions. For example in the `times` and `div` functions of this
     * interface.
     */
    fun toOutOfBoundsUnit(): OutOfBoundsUnit

    operator fun times(other: FlexibleUnit): OutOfBoundsUnit {
        return this.toOutOfBoundsUnit() * other.toOutOfBoundsUnit()
    }

    operator fun div(other: FlexibleUnit): OutOfBoundsUnit {
        return this.toOutOfBoundsUnit() / other.toOutOfBoundsUnit()
    }
}

/**
 * A unit which occurs when the realm of typesafe defined units is left. Represents values with a unit of entire meters,
 * seconds and/or kilograms.
 *
 * __This type does not have the performance guarantees of the kotlin-units library!__
 * If you can stay in the type safe environment. This should only be used when no other options are available.
 *
 * It supports basic operations like {+ - * / ==}
 */
class OutOfBoundsUnit(val rawValue: Double, val unit: PhysicsUnit): FlexibleUnit {
    /**
    * @throws IllegalArgumentException if 'this.unit' and 'other.unit' are not equal, since then no sensible behavior
     * can be defined. What should 1kg + 1m result in? If you for some reason still want to do that, get the raw values
     * of both.
     */
    operator fun plus (other: OutOfBoundsUnit): OutOfBoundsUnit {
        return OutOfBoundsUnit(rawValue + other.rawValue, unit + other.unit)
    }
    /**
     * @throws IllegalArgumentException if 'this.unit' and 'other.unit' are not equal, since then no sensible behavior
     * can be defined. What should 1N - 1s result in? If you for some reason still want to do that, get the raw values
     * of both.
     */
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
     * @returns true if the PhysicsUnits match and the values are less than 1e-9 apart from each other.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is OutOfBoundsUnit) return false
        return rawValue - other.rawValue < 1e-9 && unit == other.unit
    }

    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return this
    }

    override fun times(other: FlexibleUnit): OutOfBoundsUnit {
        return this * other.toOutOfBoundsUnit()
    }

    override fun div(other: FlexibleUnit): OutOfBoundsUnit {
        return this / other.toOutOfBoundsUnit()
    }
}


fun abs(element: OutOfBoundsUnit) = OutOfBoundsUnit(element.rawValue.absoluteValue, element.unit)

/**
 * This class represents a unit for handling kinematic operations. Only meters, seconds and kg can be represented.
 * It provides + - * / operations.
 */
class PhysicsUnit(val meterExponent: Int, val secondsExponent: Int, val kgExponent: Int) {
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
            meterExponent + other.meterExponent,
            secondsExponent + other.secondsExponent,
            kgExponent + other.kgExponent)
    }

    /**
     * @return a new PhysicsUnit with `this` exponents subtracted by `other`'s exponents.
     */
    operator fun div (other: PhysicsUnit): PhysicsUnit {
        return PhysicsUnit(
            meterExponent - other.meterExponent,
            secondsExponent - other.secondsExponent,
            kgExponent - other.kgExponent)
    }


    /**
     * @return true if all exponents are equal. Otherwise, false.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is PhysicsUnit) return false
        return meterExponent == other.meterExponent &&
                secondsExponent == other.secondsExponent &&
                kgExponent == other.kgExponent
    }

    override fun toString(): String {
        return "m^$meterExponent s^$secondsExponent kg^$kgExponent"
    }

    override fun hashCode(): Int {
        var result = meterExponent
        result = 31 * result + secondsExponent
        result = 31 * result + kgExponent
        return result
    }
}