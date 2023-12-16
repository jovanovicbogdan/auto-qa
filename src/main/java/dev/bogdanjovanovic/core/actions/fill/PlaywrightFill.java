package dev.bogdanjovanovic.core.actions.fill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PlaywrightFill {
  @JsonPropertyDescription("The selector to use to locate the element")
  @JsonProperty(required = true)
  public String locator;
  @JsonPropertyDescription("Content to fill in the element")
  @JsonProperty(required = true)
  public String content;
}
