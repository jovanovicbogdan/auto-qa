package dev.bogdanjovanovic.core.actions.locator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PlaywrightLocator {
  @JsonPropertyDescription("The description of element present in the DOM")
  @JsonProperty(required = true)
  public String elementDescription;
}
