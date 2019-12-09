package com.hd.test.common;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public enum CompareEnum
  implements IntEnumValue
{
  EQUALS(0),  GREATER(1),  LESS(-1);
  
  private CompareEnum(int val)
  {
    this.val = val;
  }
  
  private static final Map<Integer, CompareEnum> VALUE_MAP = EnumUtils.getIntValueMap(values());
  private int val;
  
  public int value()
  {
    return this.val;
  }
  
  @JsonCreator
  public static CompareEnum getEnum(int val)
  {
    return (CompareEnum)VALUE_MAP.get(Integer.valueOf(val));
  }
}