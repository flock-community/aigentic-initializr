package community.flock.wirespec.baker

import community.flock.aigentic.core.Aigentic
import community.flock.aigentic.core.agent.getFinishResponse
import community.flock.aigentic.core.agent.start
import community.flock.aigentic.core.agent.tool.Result
import community.flock.aigentic.core.annotations.AigenticParameter
import community.flock.aigentic.core.dsl.agent
import community.flock.aigentic.core.tool.Parameter
import community.flock.aigentic.core.tool.ParameterType
import community.flock.aigentic.generated.parameter.initialize
import community.flock.aigentic.openai.dsl.openAIModel
import community.flock.aigentic.openai.model.OpenAIModelIdentifier

suspend fun main() {

    @AigenticParameter
    data class Answer(val answer: String)

    Aigentic.initialize()

    fun agent(question: String) = agent {

        // Configure the model for the agent
        openAIModel {
            apiKey("YOUR_API_KEY")
            modelIdentifier(OpenAIModelIdentifier.GPT4Turbo)
        }

        // Configure the task for the agent
        task("Answer questions about Kotlin Multiplatform") {
            addInstruction("Provide concise and accurate answers")
        }

        // Set context
        context {
            addText(question)
        }

        finishResponse(
            Parameter.Primitive(
                name = "answer",
                description = "The answer to the question",
                isRequired = true,
                type = ParameterType.Primitive.String
            )
        )

    }

    // Start the agent and get a run
    val run = agent("What is cool about kotlin?").start()

    // Print the result
    when (val result = run.result) {
        is Result.Finished -> println(result.getFinishResponse<Answer>()?.answer)
        is Result.Stuck -> println("Agent is stuck: ${result.reason}")
        is Result.Fatal -> println("Error: ${result.message}")
    }
}