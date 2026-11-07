package com.demo

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(val amount: Long, val currency: String)

@Serializable
data class PaymentResponse(val valid: Boolean, val message: String?)

fun Application.configureRouting() {
    routing {
        get("/health") {
            call.respondText("OK")
        }

        post("/payments/validate") {
            val request = call.receive<PaymentRequest>()
            val result = PaymentValidator.validate(request.amount, request.currency)
            call.respond(PaymentResponse(result.isValid, result.reason))
        }
    }
}
