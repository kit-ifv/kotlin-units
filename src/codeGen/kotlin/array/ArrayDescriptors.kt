package array

/**
 * A descriptor for an array.
 * This contains the most important strings and string constructors for the generation of unit specific arrays like
 * TemperatureArray or EnergyArray.
 *
 * @param className The name of the wrapped type. E.g. Temperature or Energy.
 * @param rawValueType The name of the backing type. E.g. for Distance it is Long, for Degrees it is Double.
 * @param constructor Lambda on how to construct the wrapped type out of a raw value of a backing type.
 * @param rawValueAccess String factory for raw value access of a type.
 * @param imports All imports needed for the Array File. Sometimes it is more than just
 * 'import edu.kit.ifv.units.Impulse'
 * @param classValues List of all values the type array exposes.
 * @param functions List of all functions the type array exposes.
 * @param extensionFunctions List of all extension functions that are native to the type array file.
 */
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
        rawValueType = "Double",
    ),
    DEGREES(
        className = "Degrees",
        rawValueType = "Double",
    ),
    TEMPERATURE(
        className = "Temperature",
        rawValueType = "Long",
        functions = ArrayInternalFunction.entries - ArrayInternalFunction.SUM
    ),
    VOLUME(
        className = "Volume",
        rawValueType = "Double",
    ),
    DURATION(
        className = "Duration",
        rawValueType = "Long",
        constructor = { value -> "($value).nanoseconds" },
        rawValueAccess = { element -> "$element.inWholeNanoseconds" },
        imports= """  
            import kotlin.time.Duration
            import kotlin.time.Duration.Companion.nanoseconds
        """.trimIndent(),
    )
}