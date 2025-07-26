plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "1.9.23"
}

group = "community.flock.aigentic.initializr"
version = "0.0.0-SNAPSHOT"

val aigenticVersion = "0.2.0"

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

    // Testing
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}