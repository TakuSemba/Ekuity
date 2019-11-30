package com.takusemba.ekuity.util

import java.io.Serializable

data class Quadruple<out A, out B, out C, out D>(
  val first: A,
  val second: B,
  val third: C,
  val forth: D
) : Serializable {

  /**
   * Returns string representation of the [Triple] including its [first], [second] and [third] values.
   */
  override fun toString(): String = "($first, $second, $third)"
}