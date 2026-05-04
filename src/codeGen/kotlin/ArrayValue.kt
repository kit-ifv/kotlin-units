/**
 * All value fields a type array could have.
 */
enum class ArrayValue(val print: (className: String, type: String) -> String) {
    SIZE({ _, _ ->
        """
            /**
            * How many entries the array has.
            */
            val size: Int get() = rawValues.size
        """.trimIndent()
    }),
    INDICES({ _, _ ->
        """
            /**
            * IntRange of all valid indices of this array.
            */
            val indices: IntRange get() = rawValues.indices
        """.trimIndent()
    }),
    LAST_INDEX({ _, _ ->
        """
            /**
            * Last valid index of this array.
            */
            val lastIndex: Int get() = rawValues.lastIndex
        """.trimIndent()
    })
}