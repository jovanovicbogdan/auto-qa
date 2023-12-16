package dev.bogdanjovanovic.core.actions.assertions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PlaywrightPageAssertions {
  @JsonPropertyDescription("Page title text")
  @JsonProperty(required = true)
  public String titleText;
}
