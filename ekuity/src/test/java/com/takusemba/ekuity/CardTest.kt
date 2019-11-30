package com.takusemba.ekuity

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CardTest {

  @Test
  fun compareCards() {
    val pairs = listOf(
      Card(Rank.THREE, Suit.SPADE) to Card(Rank.TWO, Suit.SPADE),
      Card(Rank.ACE, Suit.SPADE) to Card(Rank.TWO, Suit.SPADE),
      Card(Rank.QUEEN, Suit.SPADE) to Card(Rank.JACK, Suit.SPADE),
      Card(Rank.KING, Suit.SPADE) to Card(Rank.QUEEN, Suit.SPADE),
      Card(Rank.ACE, Suit.SPADE) to Card(Rank.KING, Suit.SPADE)
    )
    for (pair in pairs) {
      val (left, right) = pair
      assertThat(left).isGreaterThan(right)
    }
  }
}