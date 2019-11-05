package com.hd.test.common;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract interface IntEnumValue
{
  @JsonValue
  public abstract int value();
}
