package dev.bogdanjovanovic.openai;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest.ChatCompletionRequestFunctionCall;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatFunction;
import com.theokanning.openai.completion.chat.ChatFunctionCall;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class OpenAI {

  private static final Logger log = Logger.getLogger(OpenAI.class.getName());
  private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
  private static final OpenAiService openAiService = new OpenAiService(OPENAI_API_KEY);

  public static ChatCompletionResult chatCompletion(final OpenAiChatModel model,
      final List<ChatMessage> messages) {
    final ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
        .builder()
        .model(model.value())
        .messages(messages)
        .maxTokens(256)
        .build();
    return openAiService.createChatCompletion(chatCompletionRequest);
  }

  public static ChatCompletionResult chatCompletion(final OpenAiChatModel model,
      final List<ChatMessage> messages, final List<ChatFunction> chatFunctions) {
    final ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
        .builder()
        .model(model.value())
        .messages(messages)
        .functions(chatFunctions)
        .functionCall(new ChatCompletionRequestFunctionCall("auto"))
        .maxTokens(256)
        .build();
    return openAiService.createChatCompletion(chatCompletionRequest);
  }

  public static ChatMessage callFunctionIfEligible(final ChatFunctionCall functionCall,
      final FunctionExecutor functionExecutor) {
    if (Objects.isNull(functionCall)) {
      log.warning("No function call found");
      return null;
    }
    final ChatMessage functionResponseMessage = functionExecutor
        .executeAndConvertToMessageHandlingExceptions(functionCall);
    final String functionResponse = functionResponseMessage.getContent();
    log.info("Function response: " + functionResponse);
    return functionResponseMessage;
  }

}
