package com.takusemba.ekuity

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DeckTest {

  @Test
  fun initialize() {
    val deck = Deck()
    assertThat(deck.cards()).hasSize(52)
  }

  @Test
  fun copy() {
    val deck = Deck()
    deck.draw()
    deck.draw()
    val copy = deck.copy()
    assertThat(copy.cards()).hasSize(50)
  }
}