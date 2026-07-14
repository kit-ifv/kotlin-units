package edu.kit.ifv.units.polynomial

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PolynomialTest {
    @Test
    fun normalizesLikeTermsAndRemovesZeros() {
        val polynomial =
            Polynomial.fromTerms(
                listOf(
                    Term(2, 3.0),
                    Term(1, 2.0),
                    Term(0, 1.0),
                    Term(2, 4.0),
                    Term(1, -2.0),
                ),
            )

        assertEquals("7.0x^2 + 1.0", polynomial.toString())
        assertEquals(2, polynomial.degree)
        assertEquals(Term(2, 7.0), polynomial.leadingTerm)
    }

    @Test
    fun addsSubtractsAndMultipliesPolynomials() {
        val p1 = Polynomial.fromTerms(listOf(Term(2, 3.0), Term(1, 2.0), Term(0, 1.0)))
        val p2 = Polynomial.fromTerms(listOf(Term(1, 3.0), Term(0, -1.0)))

        assertEquals(Polynomial.fromTerms(listOf(Term(2, 3.0), Term(1, 5.0))), p1 + p2)
        assertEquals(Polynomial.fromTerms(listOf(Term(2, 3.0), Term(1, -1.0), Term(0, 2.0))), p1 - p2)
        assertEquals(
            Polynomial.fromTerms(
                listOf(
                    Term(3, 9.0),
                    Term(2, 3.0),
                    Term(1, 1.0),
                    Term(0, -1.0),
                ),
            ),
            p1 * p2,
        )
    }

    @Test
    fun multipliesByScalar() {
        val polynomial = Polynomial.fromTerms(listOf(Term(2, 3.0), Term(1, -2.0), Term(0, 1.0)))

        assertEquals(Polynomial.fromTerms(listOf(Term(2, 6.0), Term(1, -4.0), Term(0, 2.0))), polynomial * 2)
        assertEquals(Polynomial.fromTerms(listOf(Term(2, 7.5), Term(1, -5.0), Term(0, 2.5))), polynomial * 2.5)
    }

    @Test
    fun evaluatesAndDerivesPolynomials() {
        val polynomial = Polynomial.fromTerms(listOf(Term(2, 3.0), Term(1, 2.0), Term(0, 1.0)))

        assertEquals(17.0, polynomial.evaluate(2))
        assertEquals(17.0, polynomial.evaluate(2.0))
        assertEquals(17.0, polynomial.evaluate(2f))
        assertEquals(
            Polynomial.fromTerms(listOf(Term(1, 6.0), Term(0, 2.0))),
            polynomial.derivative(),
        )
    }

    @Test
    fun dividesPolynomialsExactly() {
        val dividend = Polynomial.fromTerms(listOf(Term(2, 1.0), Term(0, -1.0)))
        val divisor = Polynomial.fromTerms(listOf(Term(1, 1.0), Term(0, -1.0)))

        val result = dividend.divRem(divisor)

        assertEquals(Polynomial.fromTerms(listOf(Term(1, 1.0), Term(0, 1.0))), result.quotient)
        assertTrue(result.remainder.isZero())
        assertEquals(result.quotient, dividend / divisor)
    }

    @Test
    fun dividesPolynomialsWithRemainder() {
        val dividend = Polynomial.fromTerms(listOf(Term(2, 1.0), Term(0, 1.0)))
        val divisor = Polynomial.fromTerms(listOf(Term(1, 1.0), Term(0, 1.0)))

        val result = dividend.divRem(divisor)

        assertEquals(Polynomial.fromTerms(listOf(Term(1, 1.0), Term(0, -1.0))), result.quotient)
        assertEquals(Polynomial.fromTerms(listOf(Term(0, 2.0))), result.remainder)
        assertFailsWith<IllegalArgumentException> { dividend / divisor }
    }

    @Test
    fun rejectsDivisionByZeroPolynomial() {
        val polynomial = Polynomial.fromTerms(listOf(Term(1, 1.0)))

        assertFailsWith<IllegalArgumentException> {
            polynomial.divRem(Polynomial.fromTerms(emptyList()))
        }
    }

    @Test
    fun testDsl() {
        val polynomial =
            polynomial {
                term { 2 * x(3) }
                term { 1.5 * x }
                term { -2 }
            }

        val polynomial2 = (2 * x(3)).term + (1.5 * x).term - 2.term

        assertEquals(
            Polynomial.fromTerms(
                listOf(
                    Term(3, 2.0),
                    Term(1, 1.5),
                    Term(0, -2.0),
                ),
            ),
            polynomial,
        )

        assertEquals(
            Polynomial.fromTerms(
                listOf(
                    Term(3, 2.0),
                    Term(1, 1.5),
                    Term(0, -2.0),
                ),
            ),
            polynomial2,
        )
    }
}
