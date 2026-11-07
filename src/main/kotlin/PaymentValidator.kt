package com.demo

data class ValidationResult(
    val isValid: Boolean,
    val reason: String? = null,
)

object PaymentValidator {
    private val supportedCurrency = setOf("INR", "USD", "AED")
    private const val MAX_AMOUNT = 10_000_000L

    fun validate(
        amount: Long,
        currency: String,
    ): ValidationResult = when {
        amount < 0 -> ValidationResult(false, "Amount must be positive")
        currency.uppercase() !in supportedCurrency -> ValidationResult(false, "Unsupported currency")
        amount > MAX_AMOUNT -> ValidationResult(false, "Amount exceeds limit")
        else -> ValidationResult(true)
    }
}
