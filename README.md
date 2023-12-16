# Auto QA

- Test automation framework that takes user prompts and finds an element or performs an action on a web page automatically.
- **Please note** that this is very basic implementation of the framework, to serve as POC, but it can be extended to support more complex actions and scenarios.

## Tech stack

- Java 17
- Gradle Build Tool
- OpenAI API
- Playwright

## How it works

- Based on user prompt, the framework will use OpenAI's Function Calling mechanism to determine which action to take.
- It can be finding an element on the page or performing an action on the element.
- For now, it can only perform small set of actions on the web elements, based on user prompt.

## How to use

- For example, if you want to find an element on a page, you can use the following prompt
```java
final PlaywrightResponse usernameInputFieldLocator = playwrightAi
    .processChatInteraction("Find the username input field on the login page");
```
- If you want to perform an action on an element, fill for example, you can use the following prompt
```java
playwrightAi
    .processChatInteraction("Fill the username input field with '%s', use the locator %s"
    .formatted("standard_user", usernameInputFieldLocator.data()));

final PlaywrightResponse passwordInputFieldLocator = playwrightAi
    .processChatInteraction("Find the password input field");
playwrightAi
    .processChatInteraction("Fill the password input field with '%s', use the locator %s"
    .formatted("secret_sauce", passwordInputFieldLocator.data()));
```
- You can also click on element
```java
final PlaywrightResponse loginButton = playwrightAi.processChatInteraction("get the login button");
playwrightAi
    .processChatInteraction("Click on login button, use the locator %s"
    .formatted(loginButton.data()));
```

- And finally you can assert on element
```java
playwrightAi.processChatInteraction("Assert that the page title is %s".formatted("Swag Labs"));
```

- You can find this example in `src/test/java/dev/bogdanjovanovic/ui/SauceDemoLoginAiTest.java`

## How to run

- Set `OPENAI_API_KEY` environment variable to your OpenAI API key
```shell
export OPENAI_API_KEY=<your key>
```
- Run tests using gradle wrapper
```shell
./gradlew clean test
```
