package edu.kit.ifv.units.arrays

enum class ArrayValue(val print: (className: String, type: String) -> String) {
    SIZE({ _, _ ->
        """
            val size: Int get() = rawValues.size
        """.trimIndent()
    })
}