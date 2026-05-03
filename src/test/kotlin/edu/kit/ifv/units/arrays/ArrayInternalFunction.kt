package edu.kit.ifv.units.arrays

/**
 * All functions a type array could expose.
 */
enum class ArrayInternalFunction(val print: (type: ArrayType) -> String) {
    GETTER({ type ->
        """
            operator fun get(index: Int) = ${type.constructor("rawValues[index]")}
        """.trimIndent()
    }),
    SETTER({ type ->
        """
            operator fun set(index: Int, value: ${type.className}) {
                rawValues[index] = ${type.rawValueAccess("value")}
            }
        """.trimIndent()
    }),
    MEAN({ type ->
        """
            fun mean() = ${type.constructor("rawValues.sum() / rawValues.size")}
        """.trimIndent()
    }),
    SUM({ type ->
        """
            fun sum() = ${type.constructor("rawValues.sum()")}
        """.trimIndent()
    }),
    ITERATOR({ type ->
        """
        fun iterator(): ${type.className}Iterator = ${type.className}Iterator(rawValues.iterator())
    """.trimIndent()
    })
}