package com.paypay.analytics.collections;

public interface Queue<T> {
  Queue<T> enQueue(T t);

  Queue<T> deQueue();

  T head();

  boolean isEmpty();
}