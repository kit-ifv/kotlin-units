package edu.kit.ifv.units.polynomial

data class Term(
    val exponent: Int,
    val c: Number,
) {
    operator fun plus(other: Term): Polynomial = Polynomial.fromTerms(listOf(this, other))

    operator fun plus(polynomial: Polynomial): Polynomial = polynomial + this

    override fun toString(): String = "${c}x^$exponent"
}
