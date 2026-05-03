package edu.kit.ifv.units.arrays

/**
 * All extension functions a type array could define for other types.
 */
enum class ArrayExtensionFunction(val print: (className: String, type: String) -> String) {
    COLLECTION_TO_TYPED_ARRAY({ className, _ ->
        """
        fun Collection<$className>.to${className}Array() = ${className}Array(this)
        """.trimIndent()
    }),
    ARRAY_TO_TYPED_ARRAY({ className, _ ->
            """
        fun Array<$className>.to${className}Array() = ${className}Array(this)
        """.trimIndent()
        }
    )
}