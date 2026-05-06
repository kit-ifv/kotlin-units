package array

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
    ANY_PREDICATE({ type ->
        """
            /**
            * @returns true if at least one element of this array matches the given predicate.
            */
            fun any(predicate: (${type.className}) -> Boolean): Boolean  = rawValues.any { predicate(${type.constructor("it")}) }
        """.trimIndent()
    }),
    NONE({ _ ->
        """
            /**
            * @returns true if this array has no elements.
            */
            fun none(): Boolean = rawValues.none()
        """.trimIndent()
    }),
    NONE_PREDICATE({ type ->
        """
            /**
            * @returns true if no element of this array matches the given predicate.
            */
            fun none(predicate: (${type.className}) -> Boolean): Boolean  = rawValues.none { predicate(${type.constructor("it")}) }
        """.trimIndent()
    }),
    ASSOCIATE({ type ->
        """
        fun <K, V> associate(transform: (${type.className}) -> Pair<K, V>): Map<K, V> 
            = rawValues.associate { transform(${type.constructor("it")}) }
    """.trimIndent()
    }),
    ASSOCIATE_BY({ type ->
        """
        fun <K> associateBy(keySelector: (${type.className}) -> K): Map<K, ${type.className}> 
            = associate { keySelector(it) to it }
    """.trimIndent()
    }),
    ASSOCIATE_BY_2({ type ->
        """
        fun <K, V> associateBy(keySelector: (${type.className}) -> K, valueTransform: (${type.className}) -> V): Map<K, V> 
            = associate { keySelector(it) to valueTransform(it)}
    """.trimIndent()
    }),
    ASSOCIATEWITH({ type ->
        """
        fun <V> associateWith(valueSelector: (${type.className}) -> V): Map<${type.className}, V> 
            = associate {it to valueSelector(it)}
    """.trimIndent()
    }),
    AVERAGE({ type ->
        """
        fun average(): ${type.className}
            = ${type.constructor("rawValues.average().to${type.rawValueType}()")}
    """.trimIndent()
    }),
    MAX({ type ->
        """
        /**
        * Returns the largest element.
        */
        fun max(): ${type.className}
            = ${type.constructor("rawValues.max()")}
    """.trimIndent()
    }),
    MIN({ type ->
        """
        /**
        * Returns the smallest element.
        */
        fun min(): ${type.className}
            = ${type.constructor("rawValues.min()")}
    """.trimIndent()
    }),
    FILTER({ type ->
        """
        /**
        * Returns a list containing only elements matching the given predicate.
        */
        fun filter(predicate: (${type.className}) -> Boolean): List<${type.className}>
            = buildList { 
                for (element in rawValues) {
                    val converted = ${type.constructor("element")}
                    if (predicate(converted)) add(converted)
                }
             }
    """.trimIndent()
    }),
    MAP({ type ->
        """
        /**
         * Returns a list containing the results of applying the given transform function to each element in the original array.
         */
         fun <R> map(transform: (${type.className}) -> R): List<R> =
            rawValues.map { transform(${type.constructor("it")}) }
        """.trimIndent()
    });
}