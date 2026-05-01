package edu.kit.ifv.units.arrays

val allArrayValues: List<ArrayFunction> = listOf(
    ArrayFunction("size getter") { _, _ ->
        """
            val size: Int get() = rawValues.size
        """.trimIndent()
    }
)