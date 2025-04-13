/*
 * Copyright © 2025 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * This class represents the Duration of time in a specific unit.
 */
package io.cdap.wrangler.api.parser;

import java.util.Locale;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class TimeDuration implements Token {
  private final String raw;
  private final long millis;

  public TimeDuration(String raw) {
    this.raw = raw;
    String unit = raw.replaceAll("[0-9.]", "").toLowerCase(Locale.ENGLISH);
    String number = raw.replaceAll("[^0-9.]", "");

    double value = Double.parseDouble(number);

    switch (unit) {
      case "ms":
        this.millis = (long) value;
        break;
      case "s":
        this.millis = (long) (value * 1000);
        break;
      case "m":
        this.millis = (long) (value * 60 * 1000);
        break;
      case "h":
        this.millis = (long) (value * 60 * 60 * 1000);
        break;
      default:
        throw new IllegalArgumentException("Unknown time duration unit: " + unit);
    }
  }

  // ✅ Add this method for the test to work
  public long getMillis() {
    return millis;
  }

  public static TimeDuration of(String input) {
    return new TimeDuration(input);
  }

  @Override
  public String value() {
    return raw;
  }

  @Override
  public JsonElement toJson() {
    return new JsonPrimitive(value());
  }

  @Override
  public TokenType type() {
    return TokenType.TIME_DURATION;
  }
}

