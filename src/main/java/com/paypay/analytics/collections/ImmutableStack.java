package com.paypay.analytics.collections;

import java.util.StringJoiner;

public class ImmutableStack<E> implements Stack<E> {

  private final E head;
  private final Stack<E> tail;
  private final int length;

  ImmutableStack(E head, Stack<E> tail) {
    this.head = head;
    this.tail = tail;
    this.length = 1 + tail.length();
  }

  @Override
  public E head() {
    return head;
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public Stack<E> tail() {
    return tail;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }


  @Override
  public String toString() {
    return new StringJoiner(", ", ImmutableStack.class.getSimpleName() + "[", "]")
      .add("head=" + head)
      .add("tail=" + tail)
      .add("length=" + length)
      .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof ImmutableStack)) { return false; }

    ImmutableStack<?> that = (ImmutableStack<?>) o;

    if (length != that.length) { return false; }
    if (!head.equals(that.head)) { return false; }
    return tail.equals(that.tail);
  }

  @Override
  public int hashCode() {
    int result = head.hashCode();
    result = 31 * result + tail.hashCode();
    result = 31 * result + length;
    return result;
  }
}