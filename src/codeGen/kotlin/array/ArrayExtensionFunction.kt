package array

/**
 * All extension functions a type array could define for other types.
 */
enum class ArrayExtensionFunction(val print: (type: ArrayType) -> String) {
    COLLECTION_TO_TYPED_ARRAY({ type ->
        """
        fun Collection<${type.className}>.to${type.className}Array() = ${type.className}Array(this)
        """.trimIndent()
    }),
    ARRAY_TO_TYPED_ARRAY({ type ->
        """
        fun Array<${type.className}>.to${type.className}Array() = ${type.className}Array(this)
        """.trimIndent()
        }
    )
}