package dev.bogdanjovanovic.core.actions.fill;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import dev.bogdanjovanovic.core.PlaywrightResponse;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class PlaywrightFillExecutorFunction implements
    BiFunction<PlaywrightFill, Page, PlaywrightResponse> {

  private static final Logger log = Logger.getLogger(PlaywrightFillExecutorFunction.class.getName());

  @Override
  public PlaywrightResponse apply(final PlaywrightFill playwrightFill, final Page page) {
    final String locator = playwrightFill.locator;
    final String content = playwrightFill.content;
    final Locator element = page.locator(locator);
    log.info("Filling element with locator %s with content %s".formatted(locator, content));
    element.fill(content);
    return null;
  }
}
