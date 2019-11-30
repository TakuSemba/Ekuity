package com.takusemba.ekuity

import org.junit.Test

class JudgementTest {

  @Test
  fun checkWithinConstraint() {
    for (i in 0..1000) {
      val deck = Deck()
      val player1 = Player(deck.draw(), deck.draw())
      val player2 = Player(deck.draw(), deck.draw())
      val board = Board()
      val game = Game(deck, listOf(player1, player2), board)
      val result = game.play()
      println(result)
    }
  }

  @Test
  fun check() {
    val judgement = Judgement(
      listOf(
        Card(rank = Rank.QUEEN, suit = Suit.SPADE),
        Card(rank = Rank.TEN, suit = Suit.SPADE),
        Card(rank = Rank.EIGHT, suit = Suit.CLUB),
        Card(rank = Rank.QUEEN, suit = Suit.SPADE),
        Card(rank = Rank.FIVE, suit = Suit.CLUB),
        Card(rank = Rank.THREE, suit = Suit.HEART),
        Card(rank = Rank.TWO, suit = Suit.HEART)
      )
    )
    val hand = judgement.judge()
    println(hand)
  }
}
