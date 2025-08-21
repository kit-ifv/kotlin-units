import units.PhysicsUnit

val meters_range = 0 .. 3
val seconds_range = -1 .. 3
val kilograms_range = 0 .. 1

/**
 * Since it isn't trivial to know how many types lead to how many operations, this program just counts them.
 * It assumes all types which have exponents in the above seen ranges, exist. All others are handled by out of bounds.
 */
fun main() {

    var numFunctionsToDefine = 0

    for(m in meters_range) {
        for( s in seconds_range) {
            for (k in kilograms_range) {
                if (m == 0 && s == 0 && k == 0) continue
                numFunctionsToDefine += countFunToDefine(
                    PhysicsUnit(m, s, k))

            }
        }
    }

    val totalTypes = (meters_range.max() - meters_range.min()) *
            (seconds_range.max() - seconds_range.min()) *
            (kilograms_range.max() - kilograms_range.min())

    val totalFunctions = totalTypes * totalTypes * 2

    println("There are in total $totalTypes types")
    println("Resulting in at max $totalFunctions functions")
    println("With these values, there are $numFunctionsToDefine functions to be defined, " +
            "the rest ${totalFunctions - numFunctionsToDefine} are covered by out of bounds operations.")

}

fun PhysicsUnit.isInRange(): Boolean {
    return this.kg_exponent in kilograms_range &&
            this.meter_exponent in meters_range &&
            this.seconds_exponent in seconds_range
}

fun countFunToDefine(unit: PhysicsUnit): Int {
    var res = 0
    for(m in unit.meter_exponent..meters_range.last()) {
        for( s in unit.seconds_exponent ..seconds_range.last()) {
            for (k in unit.kg_exponent..kilograms_range.last()) {
                if (m == 0 && s == 0 && k == 0) continue
                val t = PhysicsUnit(m, s, k)
                if ((unit * t) .isInRange()) res++
                if((unit / t) .isInRange()) res++
            }
        }
    }
    return res
}