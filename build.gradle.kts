plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.spotless)
}

detekt {
    buildUponDefaultConfig = true
    // rootPackage not enforced: demo keeps sources at src/main/kotlin root
    config.setFrom(file("detekt.yml"))
}

spotless {
    kotlin {
        target("src//**/*.kt")
        ktlint().editorConfigOverride(mapOf("ktlint_standard_no-wildcard-imports" to "disabled"))
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

group = "com.demo"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}
dependencies {
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(libs.logback.classic)

    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}
