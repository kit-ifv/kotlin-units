package edu.kit.ifv.units.arrays


data class  ArrayFunction(
    val name: String,
    val print: (className: String, type: String) -> String
)

val allFunctions: List<ArrayFunction> = listOf(
    ArrayFunction(
        "getter",
    ) { className, _ ->
    """
        operator fun get(index: Int) = $className(rawValues[index])
    """.trimIndent()
    },
    ArrayFunction(
        "setter"
    ) {className, _ ->
    """
        operator fun set(index: Int, value: $className) {
            rawValues[index] = value.rawValue
        }
    """.trimIndent()
    },
    ArrayFunction(
        "mean"
    ) {className, _ ->
    """
        fun mean() = $className(rawValues.sum() / rawValues.size)
    """.trimIndent()
    },
    ArrayFunction(
        "sum"
    ) {className, _ ->
    """
        fun sum() = $className(rawValues.sum())
    """.trimIndent()
    }
)

val allExtensionFunctions: List<ArrayFunction> = listOf(
    ArrayFunction("CollectionToTypedArray") { className, type ->
        """
        fun Collection<$className>.to${className}Array() = ${className}Array(this)
        """.trimIndent()
    },
    ArrayFunction("ArrayToTypedArray") { className, type ->
        """
        fun Array<$className>.to${className}Array() = ${className}Array(this)
        """.trimIndent()
    }
)

data class ArrayTypeDescriptor(
    val className: String,
    val rawValueType: String,
    val functions: List<ArrayFunction> = allFunctions,
    val extensionFunctions: List<ArrayFunction> = allExtensionFunctions
)

val temperatureArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Temperature",
    rawValueType = "Long"
)

val areaArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Area",
    rawValueType = "Double"
)

val volumeArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Volume",
    rawValueType = "Double"
)

val speedArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Speed",
    rawValueType = "Double"
)