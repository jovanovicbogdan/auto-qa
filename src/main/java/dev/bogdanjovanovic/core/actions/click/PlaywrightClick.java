package dev.bogdanjovanovic.core.actions.click;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PlaywrightClick {
  @JsonPropertyDescription("The selector to use to locate the element")
  @JsonProperty(required = true)
  public String locator;
}
