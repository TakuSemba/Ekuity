package com.takusemba.ekuity

import com.google.common.truth.Truth.assertThat
import com.takusemba.ekuity.Board.Stage
import org.junit.Test

class BoardTest {

  @Test
  fun state() {
    val board = Board()
    val deck = Deck()

    assertThat(board.state()).isEqualTo(Stage.PRE_FLOP)

    board.flop(deck.draw())
    board.flop(deck.draw())
    board.flop(deck.draw())

    assertThat(board.state()).isEqualTo(Stage.FLOP)

    board.flop(deck.draw())

    assertThat(board.state()).isEqualTo(Stage.TURN)

    board.flop(deck.draw())

    assertThat(board.state()).isEqualTo(Stage.LIVER)
  }

  @Test
  fun flop() {
    val card1 = Card(Rank.ACE, Suit.SPADE)
    val card2 = Card(Rank.TWO, Suit.SPADE)
    val card3 = Card(Rank.THREE, Suit.SPADE)
    val board = Board()

    board.flop(card1)
    board.flop(card2)
    board.flop(card3)

    assertThat(board.cards()).hasSize(3)
  }

  @Test
  fun copy() {
    val card1 = Card(Rank.ACE, Suit.SPADE)
    val card2 = Card(Rank.TWO, Suit.SPADE)
    val card3 = Card(Rank.THREE, Suit.SPADE)
    val board = Board()

    board.flop(card1)
    board.flop(card2)
    board.flop(card3)

    val copy = board.copy()

    assertThat(copy.cards()).hasSize(3)
    assertThat(copy.cards()).isEqualTo(board.cards())
  }
}