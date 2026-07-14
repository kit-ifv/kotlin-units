package edu.kit.ifv.units.polynomial

/*
Intuitive way of writing Terms and Polynomials. For Example:
val polynomial1 = (3 * x(2)).term + (1.5 * x).term + (-2 * x(0)).term

Also possible is:

val polynomial2 = polynomial {

    term { 3 * x(2) }
    term { 1.5 * x }
    term { -2 }
}
 */

@DslMarker
annotation class PolynomialDsl

object x {
    operator fun invoke(exponent: Int): XPower {
        require(exponent >= 0) { "Exponent must be non-negative" }
        return XPower(exponent)
    }
}

@JvmInline
value class XPower internal constructor(
    internal val exponent: Int,
)

data class TermBuilder internal constructor(
    private val coefficient: Number,
    private val exponent: Int,
) {
    val term: Term get() = Term(exponent, coefficient)
}

@JvmInline
value class CoefficientBuilder internal constructor(
    private val coefficient: Number,
) {
    val x: TermBuilder get() = TermBuilder(coefficient, 1)

    operator fun invoke(exponent: Int): TermBuilder {
        require(exponent >= 0) { "Exponent must be non-negative" }
        return TermBuilder(coefficient, exponent)
    }
}

private fun constantTerm(coefficient: Number): Term = Term(0, coefficient)

val Number.term: Term get() = constantTerm(this)

operator fun Number.times(variable: x): TermBuilder = TermBuilder(this, 1)

operator fun Number.times(power: XPower): TermBuilder = TermBuilder(this, power.exponent)

@PolynomialDsl
class PolynomialBuilder {
    private val terms = mutableListOf<Term>()

    fun term(block: () -> Any) {
        terms += block().toTerm()
    }

    private fun Any.toTerm(): Term =
        when (this) {
            is TermBuilder -> this.term
            is Term -> this
            is Number -> Term(0, this)
            else -> throw IllegalArgumentException("Unsupported type for term: ${this::class.simpleName}")
        }

    internal fun build(): Polynomial = Polynomial.fromTerms(terms)
}

fun polynomial(block: PolynomialBuilder.() -> Unit): Polynomial = PolynomialBuilder().apply(block).build()
