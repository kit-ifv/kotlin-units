@file:Suppress("unused")

package edu.kit.ifv.units.polynomial

import kotlin.math.pow

private val Term.coefficient: Double get() = c.toDouble()

@JvmInline
value class Polynomial private constructor(
    private val terms: List<Term>,
) {
    val degree: Int
        get() {
            return terms.maxOfOrNull { it.exponent } ?: 0
        }

    val leadingTerm: Term
        get() {
            return terms.maxByOrNull { it.exponent } ?: Term(0, 0.0)
        }

    operator fun plus(other: Polynomial): Polynomial = fromTerms(this.terms + other.terms)

    operator fun plus(term: Term): Polynomial = fromTerms(this.terms + term)

    operator fun minus(other: Polynomial): Polynomial = fromTerms(this.terms + other.terms.map { Term(it.exponent, -it.coefficient) })

    operator fun minus(term: Term): Polynomial = fromTerms(this.terms + Term(term.exponent, -term.coefficient))

    operator fun times(other: Polynomial): Polynomial =
        fromTerms(
            this.terms.flatMap { t1 ->
                other.terms.map { t2 ->
                    Term(t1.exponent + t2.exponent, t1.coefficient * t2.coefficient)
                }
            },
        )

    operator fun times(scalar: Number): Polynomial = fromTerms(terms.map { Term(it.exponent, it.coefficient * scalar.toDouble()) })

    operator fun times(term: Term): Polynomial = this * fromTerms(listOf(term))

    fun divRem(other: Polynomial): PolynomialDivisionResult {
        require(!other.isZero()) { "Cannot divide by zero polynomial" }

        var remainder = this
        var quotient = fromTerms(emptyList())

        while (!remainder.isZero() && remainder.degree >= other.degree) {
            val leadR = remainder.leadingTerm
            val leadD = other.leadingTerm

            val factor =
                Term(
                    exponent = leadR.exponent - leadD.exponent,
                    c = leadR.coefficient / leadD.coefficient,
                )

            quotient += factor
            remainder -= (other * factor)
        }
        return PolynomialDivisionResult(quotient.normalize(), remainder.normalize())
    }

    operator fun div(other: Polynomial): Polynomial {
        val (quotient, remainder) = this.divRem(other)
        require(remainder.isZero()) { "Polynomial division is not exact. Remainder: $remainder" }
        return quotient
    }

    fun normalize(): Polynomial = fromTerms(terms.filter { it.coefficient != 0.0 })

    fun evaluate(x: Number): Number = terms.sumOf { term -> term.coefficient * x.toDouble().pow(term.exponent) }

    fun derivative(): Polynomial =
        fromTerms(
            terms.map { term ->
                if (term.exponent == 0) {
                    Term(0, 0.0)
                } else {
                    Term(term.exponent - 1, term.coefficient * term.exponent)
                }
            },
        ).normalize()

    fun isZero(): Boolean = terms.isEmpty()

    override fun toString(): String =
        terms
            .sortedByDescending { it.exponent }
            .mapIndexed { index, term ->
                val sign =
                    when {
                        term.coefficient < 0 -> if (index == 0) "-" else " - "
                        index == 0 -> ""
                        else -> " + "
                    }

                val absCoefficient = kotlin.math.abs(term.coefficient)
                val body =
                    when (term.exponent) {
                        0 -> absCoefficient.toString()
                        1 -> if (absCoefficient == 1.0) "x" else "${absCoefficient}x"
                        else -> if (absCoefficient == 1.0) "x^${term.exponent}" else "${absCoefficient}x^${term.exponent}"
                    }

                sign + body
            }.joinToString("")

    companion object {
        fun fromTerms(terms: List<Term>): Polynomial =
            Polynomial(
                terms
                    .groupBy { it.exponent }
                    .mapValues { (_, ts) ->
                        ts.sumOf { it.coefficient }
                    }.filterValues { it != 0.0 }
                    .entries
                    .sortedBy { it.key }
                    .map { Term(it.key, it.value) },
            )
    }
}
