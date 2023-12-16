package dev.bogdanjovanovic.core.actions.locator;

import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import dev.bogdanjovanovic.core.PlaywrightResponse;
import dev.bogdanjovanovic.openai.OpenAI;
import dev.bogdanjovanovic.openai.OpenAiChatModel;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class PlaywrightLocatorExecutorFunction implements
    BiFunction<PlaywrightLocator, String, PlaywrightResponse> {

  private static final Logger log = Logger.getLogger(
      PlaywrightLocatorExecutorFunction.class.getName());

  @Override
  public PlaywrightResponse apply(final PlaywrightLocator playwrightLocator, final String html) {
    final String prompt = """
        Precisely locate the element on the page, respond just with the locator value.
        Try to use unique selectors first such as css ID and avoid using xpath. If you cannot find
        a suitable selector, respond with a "no suitable selector" message.
                
        Locate the element:
        %s
                
        in the following HTML:
        %s
        """.formatted(playwrightLocator.elementDescription, html);
    final String locator = OpenAI.chatCompletion(OpenAiChatModel.GPT_4_0613,
            List.of(new ChatMessage(ChatMessageRole.USER.value(), prompt)))
        .getChoices()
        .get(0)
        .getMessage()
        .getContent();
    log.info("Found locator %s".formatted(locator));
    return new PlaywrightResponse(locator);
  }
}
