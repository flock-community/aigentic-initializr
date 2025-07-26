import community.flock.aigentic.core.agent.start
import community.flock.aigentic.core.agent.tool.Outcome
import community.flock.aigentic.core.annotations.AigenticParameter
import community.flock.aigentic.core.dsl.agent
import community.flock.aigentic.openai.dsl.openAIModel
import community.flock.aigentic.openai.model.OpenAIModelIdentifier
import WeatherClient.WeatherResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@AigenticParameter
data class WeatherRequest(
    val location: String,
)

suspend fun main() {

    // Define a weather tool using the DSL.
    // agent<I, O> where:
    // - I (String): Input type the agent accepts in its start function (user queries as strings)
    // - O (WeatherResponse): Output type the agent produces in its Finished Outcome (weather data)
    val weatherAgent = agent<String, WeatherResponse> {

        // Configure the model for the agent, other models are also available
        openAIModel {
            apiKey("YOUR_API_KEY")
            modelIdentifier(OpenAIModelIdentifier.GPT4O)
        }

        // Configure the task for the agent
        task("Provide weather information") {
            addInstruction("Respond to user queries about weather")
        }

        // Add a weather tool
        // addTool<TI, TO> where:
        // - TI (WeatherRequest): Tool Input type the tool accepts as parameter (location data)
        // - TO (WeatherResponse): Tool Output type the tool produces as result (weather data)
        addTool("get_weather", "Get the current weather for a location") { req: WeatherRequest ->
            WeatherClient.requestWeather(req.location)
        }
    }

    // The start function expects the I type from the agent: agent<String, WeatherResponse>
    val run = weatherAgent.start("Retrieve the weather information for Amsterdam")

    // Evaluate the Runs outcome
    when (val outcome = run.outcome) {
        // Finished: Contains the successful response from the agent
        // It yields the O type from the agent: agent<String, WeatherResponse>
        // Access the response property to get the result data
        is Outcome.Finished -> println(outcome.response?.liveweer?.firstOrNull()?.verw)
        
        // Stuck: Indicates the agent couldn't proceed with execution
        // Access the reason property to understand why the agent got stuck
        is Outcome.Stuck -> println("Agent is stuck: ${outcome.reason}")
        
        // Fatal: Represents a critical error during execution
        // Access the message property to get the error details
        is Outcome.Fatal -> println("Error: ${outcome.message}")
    }

    println(run.getTokenUsageSummary())
}

object WeatherClient {

    @Serializable
    data class WeatherResponse(
        val liveweer: List<Weather>,
    ) {
        @Serializable
        data class Weather(val verw: String)
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun requestWeather(location: String): WeatherResponse =
        httpClient.get("https://weerlive.nl/api/json-data-10min.php?key=demo&locatie=${location}")
            .body<WeatherResponse>()
}
