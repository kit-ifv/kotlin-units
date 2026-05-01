package edu.kit.ifv.units.arrays

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
    },
    ArrayFunction(
        "iterator"
    ) {className, _ ->
        """
        fun iterator(): ${className}Iterator = ${className}Iterator(rawValues.iterator())
    """.trimIndent()
    }
)