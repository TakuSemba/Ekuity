package com.takusemba.ekuity

import com.takusemba.ekuity.Hand.Flush
import com.takusemba.ekuity.Hand.FullHouse
import com.takusemba.ekuity.Hand.HighCard
import com.takusemba.ekuity.Hand.OnePair
import com.takusemba.ekuity.Hand.Quads
import com.takusemba.ekuity.Hand.Straight
import com.takusemba.ekuity.Hand.StraightFlush
import com.takusemba.ekuity.Hand.Trips
import com.takusemba.ekuity.Hand.TwoPair
import org.junit.Test

class JudgementTest {

  @Test
  fun checkWithinConstraint() {
    val history = mutableListOf<Hand>()
    for (i in 0..1000) {
      val deck = Deck()
      val board = Board()
      val player1 = Player(deck.draw(), deck.draw())
      val game = Game(deck, listOf(player1), board)
      val result = game.play()
      history.add(result.hand)
    }
    println("HighCard: ${history.count { it is HighCard } / 10f}")
    println("OnePair: ${history.count { it is OnePair } / 10f}")
    println("TwoPair: ${history.count { it is TwoPair } / 10f}")
    println("Trips: ${history.count { it is Trips } / 10f}")
    println("Straight: ${history.count { it is Straight } / 10f}")
    println("Flush: ${history.count { it is Flush } / 10f}")
    println("FullHouse: ${history.count { it is FullHouse } / 10f}")
    println("Quads: ${history.count { it is Quads } / 10f}")
    println("StraightFlush: ${history.count { it is StraightFlush } / 10f}")
  }

  @Test
  fun checkDraw() {
    val history = mutableListOf<Card>()
    for (i in 0..1000) {
      val deck = Deck()
      history.add(deck.draw())
    }
    println("two: ${history.count { it.rank == Rank.TWO } / 10f}")
    println("three: ${history.count { it.rank == Rank.THREE } / 10f}")
    println("four: ${history.count { it.rank == Rank.FOUR } / 10f}")
    println("five: ${history.count { it.rank == Rank.FIVE } / 10f}")
    println("six: ${history.count { it.rank == Rank.SIX } / 10f}")
    println("seven: ${history.count { it.rank == Rank.SEVEN } / 10f}")
    println("eight: ${history.count { it.rank == Rank.EIGHT } / 10f}")
    println("nine: ${history.count { it.rank == Rank.NINE } / 10f}")
    println("ten: ${history.count { it.rank == Rank.TEN } / 10f}")
    println("jack: ${history.count { it.rank == Rank.JACK } / 10f}")
    println("queen: ${history.count { it.rank == Rank.QUEEN } / 10f}")
    println("king: ${history.count { it.rank == Rank.KING } / 10f}")
    println("ace: ${history.count { it.rank == Rank.ACE } / 10f}")
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
