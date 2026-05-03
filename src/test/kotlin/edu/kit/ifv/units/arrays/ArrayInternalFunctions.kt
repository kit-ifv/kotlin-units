package edu.kit.ifv.units.arrays

enum class ArrayInternalFunction(val print: (className: String, type: String) -> String) {
    GETTER({ className, _ ->
        """
            operator fun get(index: Int) = $className(rawValues[index])
        """.trimIndent()
    }),
    SETTER({ className, _ ->
        """
            operator fun set(index: Int, value: $className) {
                rawValues[index] = value.rawValue
            }
        """.trimIndent()
    }),
    MEAN({ className, _ ->
        """
            fun mean() = $className(rawValues.sum() / rawValues.size)
        """.trimIndent()
    }),
    SUM({ className, _ ->
        """
            fun sum() = $className(rawValues.sum())
        """.trimIndent()
    }),
    ITERATOR({ className, _ ->
        """
        fun iterator(): ${className}Iterator = ${className}Iterator(rawValues.iterator())
    """.trimIndent()
    })
}