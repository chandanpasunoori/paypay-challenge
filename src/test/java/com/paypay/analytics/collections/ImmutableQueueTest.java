package com.paypay.analytics.collections;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImmutableQueueTest {
  @Test
  public void isEmpty() {
    final var q1 = ImmutableQueue.<String>empty();
    assertTrue(q1.isEmpty());

    final var q2 = q1.enQueue("HELLO");
    assertFalse(q2.isEmpty());

    final var q3 = q2.deQueue();
    assertTrue(q3.isEmpty());
  }


  @Test
  public void checkImmutability() {
    final var q1 = ImmutableQueue.<String>empty();
    final var q2 = q1.enQueue("PAYPAY");
    final var q3 = q1.enQueue("PAYPAY");
    final var q4 = q3.deQueue();
    assertNotEquals(q1, q2);
    assertNotEquals(q1, q3);
    assertEquals(q2, q3);
    assertEquals(q1, q4);
    assertTrue(q1.isEmpty());
    assertFalse(q2.isEmpty());
    assertTrue(q4.isEmpty());
  }
}