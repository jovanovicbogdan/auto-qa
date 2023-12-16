package dev.bogdanjovanovic.core.actions.click;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import dev.bogdanjovanovic.core.PlaywrightResponse;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class PlaywrightClickExecutorFunction implements
    BiFunction<PlaywrightClick, Page, PlaywrightResponse> {

  private static final Logger log = Logger.getLogger(PlaywrightClickExecutorFunction.class.getName());

  @Override
  public PlaywrightResponse apply(final PlaywrightClick playwrightClick, final Page page) {
    final String locator = playwrightClick.locator;
    final Locator playwrightLocator = page.locator(locator);
    log.info("Click on locator %s".formatted(locator));
    playwrightLocator.click();
    return null;
  }
}
