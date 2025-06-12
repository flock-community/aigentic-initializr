# Aigentic Initializr

A starter project for building AI agents with the [Aigentic](https://github.com/flock-community/aigentic) framework in Kotlin.

## Overview

Aigentic Initializr provides a template for quickly getting started with the Aigentic framework, which allows you to build AI-powered agents in Kotlin. This project includes examples of how to create agents that can answer questions and interact with external APIs.

## Features

- Simple setup for Aigentic framework
- Examples of different agent configurations
- Integration with multiple AI providers (OpenAI, Gemini, Ollama, VertexAI)
- Tools for HTTP requests and OpenAPI integration
- Code generation with KSP (Kotlin Symbol Processing)

## Prerequisites

- JDK 21 or higher
- Kotlin 2.1.21 or higher
- API keys for the AI providers you want to use (OpenAI, Gemini, etc.)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/aigentic-initializr.git
cd aigentic-initializr
```

### 2. Configure your API keys

Replace the placeholder API keys in the example files with your actual API keys:

```kotlin
openAIModel {
    apiKey("YOUR_API_KEY") // Replace with your actual API key
    modelIdentifier(OpenAIModelIdentifier.GPT4Turbo)
}
```

### 3. Build the project

```bash
./gradlew build
```
