package com.hd.test.common;

import java.io.Serializable;
import java.util.regex.Pattern;

public class DateFormatPattern
  implements Serializable
{
  private String format;
  private Pattern pattern;
  
  public DateFormatPattern() {}
  
  public DateFormatPattern(String format, Pattern pattern)
  {
    this.format = format;
    this.pattern = pattern;
  }
  
  public String getFormat()
  {
    return this.format;
  }
  
  public void setFormat(String format)
  {
    this.format = format;
  }
  
  public Pattern getPattern()
  {
    return this.pattern;
  }
  
  public void setPattern(Pattern pattern)
  {
    this.pattern = pattern;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder("{");
    sb.append("\"format\":\"")
      .append(this.format).append('"');
    sb.append(",\"pattern\":")
      .append(this.pattern);
    sb.append('}');
    return sb.toString();
  }
}