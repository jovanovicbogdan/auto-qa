package dev.bogdanjovanovic.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import dev.bogdanjovanovic.core.PlaywrightAi;
import dev.bogdanjovanovic.core.PlaywrightResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class SauceDemoLoginAiTest {

  @Test
  void sauceDemoLogin() {
    final Playwright playwright = Playwright.create();
    final Browser browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
    final Page page = browser.newPage();
    page.navigate("https://www.saucedemo.com/");

    final PlaywrightAi playwrightAi = new PlaywrightAi(page);

    final PlaywrightResponse usernameInputFieldLocator = playwrightAi.processChatInteraction(
        "get the username input field");
    playwrightAi.processChatInteraction(
        "Fill the username input field with '%s', use the locator %s".formatted("standard_user",
            usernameInputFieldLocator.data()));

    final PlaywrightResponse passwordInputFieldLocator = playwrightAi.processChatInteraction(
        "get the password input field");
    playwrightAi.processChatInteraction(
        "Fill the password input field with '%s', use the locator %s".formatted("secret_sauce",
            passwordInputFieldLocator.data()));

    final PlaywrightResponse loginButton = playwrightAi.processChatInteraction(
        "get the login button");
    playwrightAi.processChatInteraction(
        "Click on login button, use the locator %s".formatted(loginButton.data()));

    // assert the page title
    playwrightAi.processChatInteraction(
        "Assert that the page title is %s".formatted("Swag Labs"));
  }

}
