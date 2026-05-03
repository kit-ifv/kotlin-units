package edu.kit.ifv.units.arrays

enum class ArrayType(
    val className: String,
    val rawValueType: String,
    val constructor: (value: String) -> String = { value -> "$className(${value})" },
    val rawValueAccess: (element: String) -> String = { element -> "$element.rawValue" },
    val imports: String =
        """
            import edu.kit.ifv.units.$className
        """.trimIndent(),
    val classValues: List<ArrayValue> = ArrayValue.entries,
    val functions: List<ArrayInternalFunction> = ArrayInternalFunction.entries,
    val extensionFunctions: List<ArrayExtensionFunction> = ArrayExtensionFunction.entries
) {
    ACCELERATION(
        className = "Acceleration",
        rawValueType = "Double"
    ),
    AREA(
        className = "Area",
        rawValueType = "Double"
    ),
    CUBIC_DURATION(
        className = "CubicDuration",
        rawValueType = "Double",
    ),
    CURRENCY(
        className = "Currency",
        rawValueType = "Double",
    ),
    DISTANCE(
        className = "Distance",
        rawValueType = "Long"
    ),
    ENERGY(
        className = "Energy",
        rawValueType = "Double",
    ),
    FORCE(
        className = "Force",
        rawValueType = "Double",
    ),
    FREQUENCY(
        className = "Frequency",
        rawValueType = "Double",
    ),
    IMPULSE(
        className = "Impulse",
        rawValueType = "Double",
    ),
    MASS(
        className = "Mass",
        rawValueType = "Long"
    ),
    POWER(
        className = "Power",
        rawValueType = "Double",
    ),
    SPEED(
        className = "Speed",
        rawValueType = "Double"
    ),
    SQUARE_DURATION(
        className = "SquareDuration",
        rawValueType = "Double",
    ),
    RADIANS(
        className = "Radians",
        rawValueType = "Double", // TODO this class is special, what functions does it use, or rather not use?
    ),
    DEGREES(
        className = "Degrees", // TODO this class is special, what functions does it use, or rather not use?
        rawValueType = "Double",
    ),
    TEMPERATURE(
        className = "Temperature",
        rawValueType = "Long"
    ),
    VOLUME(
        className = "Volume",
        rawValueType = "Double"
    ),
    DURATION(
        className = "Duration", // TODO Duration is different...
        rawValueType = "Long",
        constructor = { value -> "($value).nanoseconds" },
        rawValueAccess = { element -> "$element.inWholeNanoseconds" },
        imports= """  
            import kotlin.time.Duration
            import kotlin.time.Duration.Companion.nanoseconds
        """.trimIndent(),
    )
}