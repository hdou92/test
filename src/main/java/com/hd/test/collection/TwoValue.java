package com.hd.test.collection;

public class TwoValue<T1, T2>
{
  private T1 val1;
  private T2 val2;

  public TwoValue() {}

  public TwoValue(T1 val1, T2 val2)
  {
    this.val1 = val1;
    this.val2 = val2;
  }

  public T1 getVal1()
  {
    return (T1)this.val1;
  }

  public void setVal1(T1 val1)
  {
    this.val1 = val1;
  }

  public T2 getVal2()
  {
    return (T2)this.val2;
  }

  public void setVal2(T2 val2)
  {
    this.val2 = val2;
  }

  public String toString()
  {
    return "TwoValue{val1=" + this.val1 + ", val2=" + this.val2 + '}';
  }
}
