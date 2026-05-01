package edu.kit.ifv.units.arrays


data class  ArrayFunction(
    val name: String,
    val print: (className: String, type: String) -> String
)

data class ArrayTypeDescriptor(
    val className: String,
    val rawValueType: String,
    val classValues: List<ArrayFunction> = allArrayValues,
    val functions: List<ArrayFunction> = allFunctions,
    val extensionFunctions: List<ArrayFunction> = allExtensionFunctions,
)

val accelerationArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Acceleration",
    rawValueType = "Double"
)

val areaArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Area",
    rawValueType = "Double"
)

val cubicDurationArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "CubicDuration",
    rawValueType = "Double",
)

val currencyArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Currency",
    rawValueType = "Double",
)

val distanceArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Distance",
    rawValueType = "Long"
)


// TODO Duration is different...

val energyArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Energy",
    rawValueType = "Double",
)

val forceArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Force",
    rawValueType = "Double",
)

val frequencyArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Frequency",
    rawValueType = "Double",
)

val impulseArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Impulse",
    rawValueType = "Double",
)

val massArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Mass",
    rawValueType = "Long"
)

val powerArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Power",
    rawValueType = "Double",
)

val speedArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Speed",
    rawValueType = "Double"
)

val squareDurationArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "SquareDuration",
    rawValueType = "Double",
)

val radiansArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Radians",
    rawValueType = "Double", // TODO this class is special, what functions does it use, or rather not use?
)

val degreesArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Degrees",
    rawValueType = "Double",
)

val temperatureArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Temperature",
    rawValueType = "Long"
)

val volumeArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Volume",
    rawValueType = "Double"
)