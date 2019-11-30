package com.takusemba.ekuity

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RankTest {

  @Test
  fun compareRanks() {
    val pairs = listOf(
      Rank.THREE to Rank.TWO,
      Rank.ACE to Rank.TWO,
      Rank.QUEEN to Rank.JACK,
      Rank.KING to Rank.QUEEN,
      Rank.ACE to Rank.KING
    )
    for (pair in pairs) {
      val (left, right) = pair
      assertThat(left).isGreaterThan(right)
    }
  }
}