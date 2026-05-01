package edu.kit.ifv.units.arrays

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