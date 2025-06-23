package community.flock.wirespec.baker

import community.flock.aigentic.core.agent.Run
import community.flock.aigentic.core.agent.cachedInputTokens
import community.flock.aigentic.core.agent.inputTokens
import community.flock.aigentic.core.agent.outputTokens
import community.flock.aigentic.core.agent.thinkingOutputTokens

fun Run.getTokenUsageSummary(): String = """
    Token Usage:
    - Input tokens: ${inputTokens()}
    - Output tokens: ${outputTokens()}
    - Thinking output tokens: ${thinkingOutputTokens()}
    - Cached input tokens: ${cachedInputTokens()}
""".trimIndent()