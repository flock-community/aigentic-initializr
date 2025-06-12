package community.flock.wirespec.baker

import community.flock.aigentic.core.Aigentic
import community.flock.aigentic.core.agent.getFinishResponse
import community.flock.aigentic.core.agent.start
import community.flock.aigentic.core.agent.tool.Result
import community.flock.aigentic.core.annotations.AigenticParameter
import community.flock.aigentic.core.annotations.AigenticResponse
import community.flock.aigentic.core.dsl.agent
import community.flock.aigentic.generated.parameter.initialize
import community.flock.aigentic.openai.dsl.openAIModel
import community.flock.aigentic.openai.model.OpenAIModelIdentifier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@AigenticParameter
data class WeatherRequest(
    val location: String
)

@AigenticResponse
data class WeatherResponse(
    val liveweer: List<Weather>
) {
    @Serializable
    data class Weather(val verw: String)
}

suspend fun main() {

    Aigentic.initialize()

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    fun weatherUrl(location: String) = "https://weerlive.nl/api/json-data-10min.php?key=demo&locatie=${location}"

    // Define a weather tool using the DSL
    fun agent(location: String) = agent {

        // Configure the model for the agent
        openAIModel {
            apiKey("YOUR_API_KEY")
            modelIdentifier(OpenAIModelIdentifier.GPT4Turbo)
        }

        // Configure the task for the agent
        task("Provide weather information") {
            addInstruction("Respond to user queries about weather")
        }

        // Set context
        context {
            addText("Weather Information for location: $location")
        }

        // Add a weather tool
        addTool("get_weather", "Get the current weather for a location") { req: WeatherRequest ->
            // Make an API call to get weather data
            weatherUrl(req.location).run { httpClient.get(this).body<WeatherResponse>() }
        }
    }

    val run = agent("Amsterdam").start()

    // Print the result
    when (val result = run.result) {
        is Result.Finished -> println(result.getFinishResponse<WeatherResponse>()?.liveweer[0]?.verw)
        is Result.Stuck -> println("Agent is stuck: ${result.reason}")
        is Result.Fatal -> println("Error: ${result.message}")
    }
}
