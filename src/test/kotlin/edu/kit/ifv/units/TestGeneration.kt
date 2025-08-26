package edu.kit.ifv.units

import kotlin.time.Duration.Companion.seconds

val allTypes = mapOf<FlexibleUnit, String>(
    Pair(Area(1.0), "Area"),
    Pair(Acceleration(1.0), "Acceleration"),
    Pair(CubicDuration(1.0), "CubicDuration"),
    Pair( Distance(1.0), "Distance"),
    Pair(DurationWrapper(1.seconds), "Duration"),
    Pair(Energy(1.0), "Energy"),
    Pair(Frequency(1.0), "Frequency"),
    Pair(Impulse(1.0), "Impulse"),
    Pair(Mass(1), "Mass"),
    Pair(Power(1.0), "Power"),
    Pair(Newton(1.0), "Newton"),
    Pair(Speed(1.0), "Speed"),
    Pair(SquareDuration(1.0), "SquareDuration"),
    Pair(Volume(1.0), "Volume"),
    )

val allPhysicTypes = allTypes.mapKeys { (k, _) -> k.toOutOfBoundsUnit().unit }

val numberExtensionStrings = mapOf<FlexibleUnit, String>(
    Pair(Area(1.0), "1.square_meters"),
    Pair(Acceleration(1.0), "1.meters_per_second_squared"),
    Pair(CubicDuration(1.0), "1.cubic_seconds"),
    Pair( Distance(1.0), "1.meters"),
    Pair(DurationWrapper(1.seconds), "1.seconds"),
    Pair(Energy(1.0), "1.joule"),
    Pair(Frequency(1.0), "1.hertz"),
    Pair(Impulse(1.0), "1.newton_seconds"),
    Pair(Mass(1), "1.grams"),
    Pair(Power(1.0), "1.watts"),
    Pair(Newton(1.0), "1.newton"),
    Pair(Speed(1.0), "1.meters_per_second"),
    Pair(SquareDuration(1.0), "1.square_seconds"),
    Pair(Volume(1.0), "1.cubicMeters"),
)

val allPhysicTypesToNumberExtensionStrings = numberExtensionStrings.mapKeys { (k, _) -> k.toOutOfBoundsUnit().unit }

/**
 * Since it is kinda hard to remember which operations for which type need to be defined and tested for, this is a
 * little tool, that helps with that. It outputs all testcases for all binary operations, which ensure full
 * functionality.
 */
fun main() {

    var numFunctionsToDefine = 0

    println("Following tests test for defined types as a result:")

    for(type in allPhysicTypes.keys) {
        for(otherType in allPhysicTypes.keys) {
            val mul = type * otherType
            if (mul.isDefinedType()) {
                numFunctionsToDefine++
                println("assertIs<${allPhysicTypes[mul]}>(${allPhysicTypesToNumberExtensionStrings[type]} * " +
                        "${allPhysicTypesToNumberExtensionStrings[otherType]})")
            }
            val div = type / otherType
            if (div.isDefinedType()) {
                numFunctionsToDefine++
                println("assertIs<${allPhysicTypes[div]}>(${allPhysicTypesToNumberExtensionStrings[type]} / " +
                        "${allPhysicTypesToNumberExtensionStrings[otherType]})")
            }
        }
    }

    println("\nFollowing tests test for OutOfBoundsUnit as a result:")
    for(type in allPhysicTypes.keys) {
        var printType = allPhysicTypesToNumberExtensionStrings[type]
        if (type == PhysicsUnit(0,1,0)) {
            printType += ".wrap" //duration needs the wrapper to work with OutOfBoundsUnit
        }
        for(otherType in allPhysicTypes.keys) {
            if (otherType == type) continue
            val mul = type * otherType

            var printOther = allPhysicTypesToNumberExtensionStrings[otherType]
            if (otherType == PhysicsUnit(0,1,0)) {
                printOther += ".wrap" //duration needs the wrapper to work with OutOfBoundsUnit
            }
            if (!mul.isDefinedType()) {
                println("assertIs<OutOfBoundsUnit>($printType * " +
                        "$printOther)")
            }
            val div = type / otherType
            if (!div.isDefinedType()) {
                println("assertIs<OutOfBoundsUnit>($printType / " +
                        "$printOther)")
            }
        }
    }


    val totalTypes = allTypes.count()
    val totalFunctions = totalTypes * totalTypes

    println("There are in total $totalTypes real types")
    println("Resulting in at max $totalFunctions real functions")
    println("With these types, there are $numFunctionsToDefine functions to be defined, " +
            "the rest ${totalFunctions - numFunctionsToDefine} are covered by out of bounds operations.")

}

fun PhysicsUnit.isDefinedType(): Boolean {
    return allPhysicTypes.keys.contains(this)
}