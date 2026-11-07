package com.demo

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ServerTest {

    private fun runTest(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        application {
            configureSerialization()
            configureRouting()
        }
        block()
    }

    @Test
    fun `health endpoint returns OK`() = runTest {
        val response = client.get("/health")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("OK", response.bodyAsText())
    }

    @Test
    fun `valid payment is accepted`() = runTest {
        val response = client.post("/payments/validate") {
            contentType(ContentType.Application.Json)
            setBody("""{"amount":5000,"currency":"INR"}""")
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("\"valid\":true"))
    }

    @Test
    fun `negative payment is rejected with reason`() = runTest {
        val response = client.post("/payments/validate") {
            contentType(ContentType.Application.Json)
            setBody("""{"amount":-100,"currency":"INR"}""")
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("\"valid\":false"))
        assertTrue(body.contains("Amount must be positive"))
    }
}