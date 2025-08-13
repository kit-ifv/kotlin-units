package units

/**
 * A unit which occurs when the realm of typesafe defined units is left.
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
 * This class represents a unit for handling kinematic operations.
 * It provides + - * / operations.
 */
class PhysicsUnit(val meter_exponent: Int, val seconds_exponent: Int, val weight_exponent: Int) {
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
            weight_exponent + other.weight_exponent)
    }

    /**
     * @return a new PhysicsUnit with `this` exponents subtracted by `other`'s exponents.
     */
    operator fun div (other: PhysicsUnit): PhysicsUnit {
        return PhysicsUnit(
            meter_exponent - other.meter_exponent,
            seconds_exponent - other.seconds_exponent,
            weight_exponent - other.weight_exponent)
    }


    /**
     * @return true if all exponents are equal. Otherwise, false.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is PhysicsUnit) return false
        return meter_exponent == other.meter_exponent &&
                seconds_exponent == other.seconds_exponent &&
                weight_exponent == other.weight_exponent
    }

    override fun toString(): String {
        return "m^$meter_exponent s^$seconds_exponent kg^$weight_exponent"
    }
}