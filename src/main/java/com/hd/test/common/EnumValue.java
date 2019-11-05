package com.hd.test.common;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract interface EnumValue<T>
{
  @JsonValue
  public abstract T value();
}
