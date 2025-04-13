/*
 * Copyright © 2025 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */

package io.cdap.wrangler.api.parser;

import java.util.Locale;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
/**
 * This class represents the size of a byte in a specific unit.
 */

public class ByteSize implements Token {
  private final String raw;
  private final double value;
  private final long bytes;

  public ByteSize(String raw) {
    this.raw = raw;

    String unit = raw.replaceAll("[0-9.]", "").toUpperCase(Locale.ENGLISH);
    String number = raw.replaceAll("[^0-9.]", "");

    this.value = Double.parseDouble(number);

    switch (unit) {
      case "B":
        this.bytes = (long) value;
        break;
      case "KB":
        this.bytes = (long) (value * 1024);
        break;
      case "MB":
        this.bytes = (long) (value * 1024 * 1024);
        break;
      case "GB":
        this.bytes = (long) (value * 1024 * 1024 * 1024);
        break;
      case "TB":
        this.bytes = (long) (value * 1024L * 1024L * 1024L * 1024L);
        break;
      default:
        throw new IllegalArgumentException("Unknown byte size unit: " + unit);
    }
  }
  public static ByteSize of(String input) {
    return new ByteSize(input);
  }

  public long getBytes() {
    return bytes;
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
    return TokenType.BYTE_SIZE;
  }
}
