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
    }),
    ALL({ type ->
        """
            /**
            * @returns true if all elements of this array match the given predicate.
            */
            fun all(predicate: (${type.className}) -> Boolean): Boolean 
                = rawValues.all { predicate(${type.constructor("it")}) }
        """.trimIndent()
    }),
    ANY({ _ ->
        """
            /**
            * @returns true if this array contains at least one element.
            */
            fun any(): Boolean = rawValues.any()
        """.trimIndent()
    }),
    ANY_PREDICATE({type ->
        """
            /**
            * @returns true if at least one element of this array matches the given predicate.
            */
            fun any(predicate: (${type.className}) -> Boolean): Boolean  = rawValues.any { predicate(${type.constructor("it")}) }
        """.trimIndent()
    })
}