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
"""
    },
    ArrayFunction(
        "setter"
    ) {className, _ ->
"""
    operator fun set(index: Int, value: $className) {
        rawValues[index] = value.rawValue
    }
"""
    },
    ArrayFunction(
        "mean"
    ) {className, _ ->
"""
    fun mean() = $className(rawValues.sum() / rawValues.size)
"""
    },
    ArrayFunction(
        "sum"
    ) {className, _ ->
"""
    fun sum() = $className(rawValues.sum())
"""
    }

)

data class ArrayTypeDescriptor(
    val className: String,
    val rawValueType: String,
    val functions: List<ArrayFunction> = allFunctions
)

val temperatureArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Temperature",
    rawValueType = "Long"
)

val areaArray: ArrayTypeDescriptor = ArrayTypeDescriptor(
    className = "Area",
    rawValueType = "Double"
)