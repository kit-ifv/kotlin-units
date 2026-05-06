@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

/**
* This class is a typed array for Duration. At runtime, it is converted to a regular java array
* of the backing type long (long[]).
*/
@JvmInline
value class DurationArray internal constructor(private val rawValues: LongArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(LongArray(size))
    
    constructor(size: Int, init: (index: Int) -> Duration): 
        this(LongArray(size) { index -> init(index).inWholeNanoseconds })
        
    constructor(src: Array<Duration>): this(src.map { it.inWholeNanoseconds }.toLongArray())
    
    constructor(src: Collection<Duration>): this(src.map { it.inWholeNanoseconds }.toLongArray())

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

    operator fun get(index: Int) = (rawValues[index]).nanoseconds

    operator fun set(index: Int, value: Duration) {
        rawValues[index] = value.inWholeNanoseconds
    }

    fun mean() = (rawValues.sumOf { it } / rawValues.size).nanoseconds

    fun sum() = (rawValues.sumOf { it }).nanoseconds

    fun iterator(): DurationIterator = DurationIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Duration) -> Boolean): Boolean 
        = rawValues.all { predicate((it).nanoseconds) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Duration) -> Boolean): Boolean  = rawValues.any { predicate((it).nanoseconds) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Duration) -> Boolean): Boolean  = rawValues.none { predicate((it).nanoseconds) }

    fun <K, V> associate(transform: (Duration) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform((it).nanoseconds) }

    fun <K> associateBy(keySelector: (Duration) -> K): Map<K, Duration> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Duration) -> K, valueTransform: (Duration) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Duration) -> V): Map<Duration, V> 
        = associate {it to valueSelector(it)}

    fun average(): Duration = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Duration
        = (rawValues.max()).nanoseconds

    /**
    * Returns the smallest element.
    */
    fun min(): Duration
        = (rawValues.min()).nanoseconds

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Duration) -> Boolean): List<Duration>
        = buildList { 
            for (element in rawValues) {
                val converted = (element).nanoseconds
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Duration) -> R): List<R> =
        rawValues.map { transform((it).nanoseconds) }

    companion object {
        @JvmInline
        value class DurationIterator internal constructor(val iterator: LongIterator): Iterator<Duration> {
            override fun next(): Duration = (iterator.next()).nanoseconds
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Duration>.toDurationArray() = DurationArray(this)

fun Array<Duration>.toDurationArray() = DurationArray(this)