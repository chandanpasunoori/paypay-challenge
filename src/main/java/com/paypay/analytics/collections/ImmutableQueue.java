package com.paypay.analytics.collections;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class ImmutableQueue<T> implements Queue<T> {
  private static final ImmutableQueue<?> EMPTY = new ImmutableQueue<>(Stack.empty(), Stack.empty());

  private final Stack<T> first;
  private final Stack<T> last;

  private ImmutableQueue(Stack<T> first, Stack<T> last) {
    this.first = first.isEmpty() ? last.reverse() : first;
    this.last = first.isEmpty() ? first : last;
  }

  @SuppressWarnings("unchecked")
  public static <T> ImmutableQueue<T> empty() {
    return (ImmutableQueue<T>) EMPTY;
  }

  @Override
  public Queue<T> enQueue(T element) {
    return new ImmutableQueue<>(first, last.push(element));
  }

  @Override
  public Queue<T> deQueue() {
    if (isEmpty()) { throw new NoSuchElementException("no elements present in queue"); }
    return new ImmutableQueue<>(first.tail(), last);
  }

  @Override
  public T head() {
    if (isEmpty()) { throw new NoSuchElementException("no elements present in queue"); }
    return first.head();
  }

  @Override
  public boolean isEmpty() {
    return first.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof ImmutableQueue)) { return false; }

    ImmutableQueue<?> that = (ImmutableQueue<?>) o;

    if (!first.equals(that.first)) { return false; }
    return last.equals(that.last);
  }

  @Override
  public int hashCode() {
    int result = first.hashCode();
    result = 31 * result + last.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ImmutableQueue.class.getSimpleName() + "[", "]")
      .add("first=" + first)
      .add("last=" + last)
      .toString();
  }
}
