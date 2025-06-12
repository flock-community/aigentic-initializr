plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "1.9.23"
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
}

group = "community.flock.aigentic.initializr"
version = "0.1.0-SNAPSHOT"

val aigenticVersion = "0.1.0-SNAPSHOT"
val ktorVersion = "2.3.13"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {

    // Core module
    implementation("community.flock.aigentic:core:${aigenticVersion}")

    // Provider modules (choose the ones you need)
    implementation("community.flock.aigentic:openai:${aigenticVersion}")
    implementation("community.flock.aigentic:gemini:${aigenticVersion}")
    implementation("community.flock.aigentic:ollama:${aigenticVersion}")
    implementation("community.flock.aigentic:vertexai:${aigenticVersion}")

    // Tools modules (optional)
    implementation("community.flock.aigentic:http:${aigenticVersion}")
    implementation("community.flock.aigentic:openapi:${aigenticVersion}")

    // Ktor client
//    implementation("io.ktor:ktor-client-content-negotiation:${ktorVersion}")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktorVersion}")

    // KSP processor
    ksp("community.flock.aigentic:ksp-processor:${aigenticVersion}")

    // Testing
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}