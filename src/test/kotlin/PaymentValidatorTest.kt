package com.demo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PaymentValidatorTest {
    @Test
    fun `valid payment is accepted`() {
        assertTrue(PaymentValidator.validate(5_000, "INR").isValid)
    }

    @Test
    fun `negative amount is rejected`() {
        val result = PaymentValidator.validate(-100, "INR")
        assertFalse(result.isValid)
        assertEquals("Amount must be positive", result.reason)
    }

    @Test
    fun `zero amount is rejected`() {
        assertFalse(PaymentValidator.validate(0, "INR").isValid)
    }

    @Test
    fun `unsupported currency is rejected`() {
        val result = PaymentValidator.validate(5_000, "XYZ")
        assertFalse(result.isValid)
        assertEquals("Unsupported currency", result.reason)
    }

    @Test
    fun `amount over limit is rejected`() {
        val result = PaymentValidator.validate(10_000_001, "USD")
        assertFalse(result.isValid)
        assertEquals("Amount exceeds limit", result.reason)
    }

    @Test
    fun `currency check is case insensitive`() {
        assertTrue(PaymentValidator.validate(5_000, "inr").isValid)
    }
}
