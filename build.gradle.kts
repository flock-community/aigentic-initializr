plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("com.diffplug.spotless") version "7.2.1"
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

spotless {
    kotlin {
        ktlint()
        target("**/*.kt")
        targetExclude("build/**/*.kt")
        endWithNewline()
        trimTrailingWhitespace()
    }
}
