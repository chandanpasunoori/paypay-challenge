package com.paypay.analytics.collections;

import java.util.NoSuchElementException;

public interface Stack<T> {
  static <T> Stack<T> empty() { return EmptyStack.emptyStack(); }

  boolean isEmpty();

  T head();

  int length();

  Stack<T> tail();

  default Stack<T> push(T element) {
    return new ImmutableStack<>(element, this);
  }

  default Stack<T> reverse() { return reverse(empty()); }

  default Stack<T> reverse(Stack<T> mat) {
    return isEmpty() ? mat : tail().reverse(mat.push(head()));
  }

  class EmptyStack<T> implements Stack<T> {

    private static final EmptyStack<?> EMPTY_STACK = new EmptyStack<>();

    private EmptyStack() {}

    @SuppressWarnings("unchecked")
    static <T> EmptyStack<T> emptyStack() {
      return (EmptyStack<T>) EMPTY_STACK;
    }

    @Override
    public T head() { throw new NoSuchElementException("no elements in stack"); }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public Stack<T> tail() {
      throw new UnsupportedOperationException("empty");
    }

    @Override
    public boolean isEmpty() { return true; }

    @Override
    public String toString() {
      return "EMPTY";
    }
  }
}
