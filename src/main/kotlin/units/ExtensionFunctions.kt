package units

fun <T: Comparable<T>> min(a: T, b: T): T {
    if(a <= b) return a
    return b
}

fun <T: Comparable<T>> max(a: T, b: T): T {
    if(a >= b) return a
    return b
}

