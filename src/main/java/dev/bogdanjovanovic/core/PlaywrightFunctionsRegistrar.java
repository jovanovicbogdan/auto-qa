package dev.bogdanjovanovic.core;

import com.microsoft.playwright.Page;
import com.theokanning.openai.completion.chat.ChatFunction;
import dev.bogdanjovanovic.core.actions.assertions.PlaywrightPageAssertions;
import dev.bogdanjovanovic.core.actions.assertions.PlaywrightPageAssertionsExecutorFunction;
import dev.bogdanjovanovic.core.actions.click.PlaywrightClick;
import dev.bogdanjovanovic.core.actions.click.PlaywrightClickExecutorFunction;
import dev.bogdanjovanovic.core.actions.fill.PlaywrightFill;
import dev.bogdanjovanovic.core.actions.fill.PlaywrightFillExecutorFunction;
import dev.bogdanjovanovic.core.actions.locator.PlaywrightLocator;
import dev.bogdanjovanovic.core.actions.locator.PlaywrightLocatorExecutorFunction;
import java.util.ArrayList;
import java.util.List;

public class PlaywrightFunctionsRegistrar {

  private final List<ChatFunction> chatFunctions = new ArrayList<>();
  private final Page page;

  public PlaywrightFunctionsRegistrar(final Page page) {
    this.page = page;
    registerPlaywrightFunctions();
  }

  public List<ChatFunction> getChatFunctions() {
    return chatFunctions;
  }

  private void registerPlaywrightFunctions() {
    buildAndAddLocateElementPlaywrightFunction();
    buildAndAddClickElementPlaywrightFunction();
    buildAndAddFillElementPlaywrightFunction();
    buildAndAddAssertPageTitlePlaywrightFunction();
  }

  private void buildAndAddLocateElementPlaywrightFunction() {
    final PlaywrightLocatorExecutorFunction pwLocatorExecutorFunction = new PlaywrightLocatorExecutorFunction();
    final ChatFunction locateElementChatFunction = ChatFunction
        .builder()
        .name("locate_element")
        .description("Locate the element on the page")
        .executor(PlaywrightLocator.class,
            pwLocator -> pwLocatorExecutorFunction.apply(pwLocator, page.content()))
        .build();
    chatFunctions.add(locateElementChatFunction);
  }

  private void buildAndAddClickElementPlaywrightFunction() {
    final PlaywrightClickExecutorFunction pwClickExecutorFunction = new PlaywrightClickExecutorFunction();
    final ChatFunction clickElementChatFunction = ChatFunction
        .builder()
        .name("click_element")
        .description("Perform a click on the element")
        .executor(PlaywrightClick.class,
            pwClick -> pwClickExecutorFunction.apply(pwClick, page))
        .build();
    chatFunctions.add(clickElementChatFunction);
  }

  private void buildAndAddFillElementPlaywrightFunction() {
    final PlaywrightFillExecutorFunction pwFillExecutorFunction = new PlaywrightFillExecutorFunction();
    final ChatFunction fillElementChatFunction = ChatFunction
        .builder()
        .name("fill_element")
        .description("Fill the element with the given content")
        .executor(PlaywrightFill.class, pwFill -> pwFillExecutorFunction.apply(pwFill, page))
        .build();
    chatFunctions.add(fillElementChatFunction);
  }

  private void buildAndAddAssertPageTitlePlaywrightFunction() {
    final PlaywrightPageAssertionsExecutorFunction pwPageAssertionsExecutorFunction = new PlaywrightPageAssertionsExecutorFunction();
    final ChatFunction assertPageTitleChatFunction = ChatFunction
        .builder()
        .name("assert_page_title")
        .description("Assert the page title")
        .executor(PlaywrightPageAssertions.class,
            pwPageAssertions -> pwPageAssertionsExecutorFunction.apply(pwPageAssertions, page))
        .build();
    chatFunctions.add(assertPageTitleChatFunction);
  }

}
