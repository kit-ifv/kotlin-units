package edu.kit.ifv.units.arrays

/**
 * All value fields a type array could have.
 */
enum class ArrayValue(val print: (className: String, type: String) -> String) {
    SIZE({ _, _ ->
        """
            val size: Int get() = rawValues.size
        """.trimIndent()
    })
}