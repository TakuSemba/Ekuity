package com.takusemba.ekuity

import org.junit.Test

class JudgementTest {

  @Test
  fun checkWithinConstraint() {
    val deck = Deck()
    val player1 = Player(deck.draw(), deck.draw())
    val player2 = Player(deck.draw(), deck.draw())
    val board = Board()
    for (i in 0..1000) {
      val game = Game(listOf(player1, player2), board)
      val result = game.play()
      println(result)
    }
  }
}
