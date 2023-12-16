package dev.bogdanjovanovic.core.actions.assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Page;
import dev.bogdanjovanovic.core.PlaywrightResponse;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class PlaywrightPageAssertionsExecutorFunction implements
    BiFunction<PlaywrightPageAssertions, Page, PlaywrightResponse> {

  private static final Logger log = Logger.getLogger(
      PlaywrightPageAssertionsExecutorFunction.class.getName());

  @Override
  public PlaywrightResponse apply(final PlaywrightPageAssertions playwrightPageAssertions,
      final Page page) {
    final String expected = playwrightPageAssertions.titleText;
    log.info("Assert page title is %s".formatted(expected));
    assertThat(page).hasTitle(expected);
    return null;
  }
}
