package dev.bogdanjovanovic.openai;

public enum OpenAiChatModel {
  // gpt-3.5-turbo-1106 with context window of 16,385 tokens, training data up to Sep 2021
  GPT_35_TURBO_1106("gpt-3.5-turbo-1106"),
  // gpt-4-0613 with context window of 8,192 tokens, training data up to Sep 2021
  GPT_4_0613("gpt-4-0613");

  private final String value;

  OpenAiChatModel(final String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
