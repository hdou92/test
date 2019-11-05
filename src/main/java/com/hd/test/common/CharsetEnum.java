package com.hd.test.common;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.nio.charset.Charset;
import java.util.Map;

public enum CharsetEnum
  implements EnumValue<String>
{
  ASCII("ASCII"),  GBK("GBK"),  GB2312("GB2312"),  UNICODE("Unicode"),  UTF8("UTF-8"),  UTF16("UTF-16"),  UTF32("UTF-32"),  UTF16_BIG_ENCIAN("UTF-16BE"),  UTF16_LITTLE_ENCIAN("UTF-16LE");

  private CharsetEnum(String val)
  {
    this.val = val;
  }

  private static final Map<String, CharsetEnum> VALUE_MAP = EnumUtils.getValueMap(values());
  private String val;

  public String value()
  {
    return this.val;
  }

  @JsonCreator
  public static CharsetEnum getEnum(String val)
  {
    return (CharsetEnum)VALUE_MAP.get(val);
  }

  public Charset getCharset()
  {
    return Charset.forName(value());
  }
}
