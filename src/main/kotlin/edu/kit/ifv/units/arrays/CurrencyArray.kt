@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Currency

/**
* This class is a typed array for Currency. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class CurrencyArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Currency): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Currency>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Currency>): this(src.map { it.rawValue }.toDoubleArray())

    /**
    * How many entries the array has.
    */
    val size: Int get() = rawValues.size

    /**
    * IntRange of all valid indices of this array.
    */
    val indices: IntRange get() = rawValues.indices

    /**
    * Last valid index of this array.
    */
    val lastIndex: Int get() = rawValues.lastIndex

    operator fun get(index: Int) = Currency(rawValues[index])

    operator fun set(index: Int, value: Currency) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Currency(rawValues.sum() / rawValues.size)

    fun sum() = Currency(rawValues.sum())

    fun iterator(): CurrencyIterator = CurrencyIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Currency) -> Boolean): Boolean 
        = rawValues.all { predicate(Currency(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Currency) -> Boolean): Boolean  = rawValues.any { predicate(Currency(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Currency) -> Boolean): Boolean  = rawValues.none { predicate(Currency(it)) }

    fun <K, V> associate(transform: (Currency) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Currency(it)) }

    fun <K> associateBy(keySelector: (Currency) -> K): Map<K, Currency> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Currency) -> K, valueTransform: (Currency) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Currency) -> V): Map<Currency, V> 
        = associate {it to valueSelector(it)}

    fun average(): Currency
        = Currency(rawValues.average().toDouble())

    /**
    * Returns the largest element.
    */
    fun max(): Currency
        = Currency(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Currency
        = Currency(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Currency) -> Boolean): List<Currency>
        = buildList { 
            for (element in rawValues) {
                val converted = Currency(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Currency) -> R): List<R> =
        rawValues.map { transform(Currency(it)) }

    companion object {
        @JvmInline
        value class CurrencyIterator internal constructor(val iterator: DoubleIterator): Iterator<Currency> {
            override fun next(): Currency = Currency(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Currency>.toCurrencyArray() = CurrencyArray(this)

fun Array<Currency>.toCurrencyArray() = CurrencyArray(this)