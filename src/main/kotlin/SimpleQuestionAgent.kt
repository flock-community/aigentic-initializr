import community.flock.aigentic.core.agent.start
import community.flock.aigentic.core.agent.tool.Outcome
import community.flock.aigentic.core.annotations.AigenticParameter
import community.flock.aigentic.core.dsl.agent
import community.flock.aigentic.openai.dsl.openAIModel
import community.flock.aigentic.openai.model.OpenAIModelIdentifier

suspend fun main() {
    @AigenticParameter
    data class Answer(
        val answer: String,
    )

    // Define a question answering agent using the DSL.
    // agent<I, O> where:
    // - I (String): Input type the agent accepts in its start function (user questions as strings)
    // - O (Answer): Output type the agent produces in its Finished Outcome (answer data)
    val questionAgent =
        agent<String, Answer> {
            // Configure the model for the agent, other models are also available
            openAIModel {
                apiKey("YOUR_API_KEY")
                modelIdentifier(OpenAIModelIdentifier.GPT5_5)
            }

            // Configure the task for the agent
            // This defines the agent's purpose and provides instructions for its behavior
            task("Answer questions about Kotlin Multiplatform") {
                addInstruction("Provide concise and accurate answers")
            }
        }

    // The start function expects the I type from the agent: agent<String, Answer>
    val run = questionAgent.start("What is cool about kotlin?")

    // Evaluate the Runs outcome
    when (val outcome = run.outcome) {
        // Finished: Contains the successful response from the agent
        // It yields the O type from the agent: agent<String, Answer>
        // Access the response property to get the answer
        is Outcome.Finished -> println(outcome.response?.answer)

        // Stuck: Indicates the agent couldn't proceed with execution
        // Access the reason property to understand why the agent got stuck
        is Outcome.Stuck -> println("Agent is stuck: ${outcome.reason}")

        // Fatal: Represents a critical error during execution
        // Access the message property to get the error details
        is Outcome.Fatal -> println("Error: ${outcome.message}")
    }

    // Print token usage summary to monitor resource consumption
    println(run.getTokenUsageSummary())
}
