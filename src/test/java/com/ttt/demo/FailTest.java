package com.ttt.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FailTest {

  @Test
  void testSanityCheck() {
    int expected = 10;
    int actual = 10;
    assertEquals(expected, actual, "Giá trị không khớp!");
  }
}