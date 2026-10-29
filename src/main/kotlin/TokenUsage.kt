import community.flock.aigentic.core.agent.Run
import community.flock.aigentic.core.agent.tokenUsage

fun Run<*>.getTokenUsageSummary(): String =
    tokenUsage().let { usage ->
        """
        Token Usage:
        - Input tokens: ${usage.inputTokens}
        - Output tokens: ${usage.outputTokens}
        - Thinking output tokens: ${usage.thinkingOutputTokens}
        - Cached input tokens: ${usage.cachedInputTokens}
        """.trimIndent()
    }
