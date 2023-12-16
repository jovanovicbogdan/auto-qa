package dev.bogdanjovanovic.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatFunction;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.FunctionExecutor;
import dev.bogdanjovanovic.openai.OpenAI;
import dev.bogdanjovanovic.openai.OpenAiChatModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class PlaywrightAi {

  private static final Logger log = Logger.getLogger(PlaywrightAi.class.getName());
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final List<ChatMessage> messages = new ArrayList<>();
  private final List<ChatFunction> chatFunctions;
  private final FunctionExecutor functionExecutor;

  public PlaywrightAi(final Page page) {
    this.chatFunctions = new PlaywrightFunctionsRegistrar(page).getChatFunctions();
    this.functionExecutor = new FunctionExecutor(chatFunctions);
    addSystemMessage("You are helpful assistant that will provide unique locators for the elements "
        + "on the page as well as perform actions on the page.");
  }

  public PlaywrightResponse processChatInteraction(final String prompt) {
    addUserMessage(prompt);
    final ChatCompletionResult chatCompletionResult = OpenAI.chatCompletion(
        OpenAiChatModel.GPT_4_0613, messages, chatFunctions);
    final String content = chatCompletionResult.getChoices().get(0).getMessage().getContent();
    if (!Objects.isNull(content)) {
      addAiMessage(content);
    }
    final ChatMessage functionResponseMessage = OpenAI.callFunctionIfEligible(
        chatCompletionResult.getChoices().get(0).getMessage().getFunctionCall(),
        functionExecutor);
    if (!Objects.isNull(functionResponseMessage)) {
      addFunctionMessage(functionResponseMessage.getContent(),
          functionResponseMessage.getName());
      try {
        return objectMapper.readValue(functionResponseMessage.getContent(), PlaywrightResponse.class);
      } catch (JsonProcessingException e) {
        log.warning("Failed to parse function response message");
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  private void addUserMessage(final String message) {
    log.info("Add USER message %s".formatted(message));
    messages.add(new ChatMessage(ChatMessageRole.USER.value(), message));
  }

  private void addAiMessage(final String message) {
    log.info("Add ASSISTANT message %s".formatted(message));
    messages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(), message));
  }

  private void addSystemMessage(final String message) {
    log.info("Add SYSTEM message %s".formatted(message));
    messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), message));
  }

  private void addFunctionMessage(final String message, final String name) {
    log.info("Add FUNCTION message %s with name %s".formatted(message, name));
    messages.add(new ChatMessage(ChatMessageRole.FUNCTION.value(), message, name));
  }

}
