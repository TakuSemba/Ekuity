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
    println("HighCard: ${history.count { it is HighCard }}")
    println("OnePair: ${history.count { it is OnePair }}")
    println("TwoPair: ${history.count { it is TwoPair }}")
    println("Trips: ${history.count { it is Trips }}")
    println("Straight: ${history.count { it is Straight }}")
    println("Flush: ${history.count { it is Flush }}")
    println("FullHouse: ${history.count { it is FullHouse }}")
    println("Quads: ${history.count { it is Quads }}")
    println("StraightFlush: ${history.count { it is StraightFlush }}")
  }

  @Test
  fun checkDraw() {
    val history = mutableListOf<Card>()
    for (i in 0..1000) {
      val deck = Deck()
      history.add(deck.draw())
    }
    println("two: ${history.count { it.rank == Rank.TWO }}")
    println("three: ${history.count { it.rank == Rank.THREE }}")
    println("four: ${history.count { it.rank == Rank.FOUR }}")
    println("five: ${history.count { it.rank == Rank.FIVE }}")
    println("six: ${history.count { it.rank == Rank.SIX }}")
    println("seven: ${history.count { it.rank == Rank.SEVEN }}")
    println("eight: ${history.count { it.rank == Rank.EIGHT }}")
    println("nine: ${history.count { it.rank == Rank.NINE }}")
    println("ten: ${history.count { it.rank == Rank.TEN }}")
    println("jack: ${history.count { it.rank == Rank.JACK }}")
    println("queen: ${history.count { it.rank == Rank.QUEEN }}")
    println("king: ${history.count { it.rank == Rank.KING }}")
    println("ace: ${history.count { it.rank == Rank.ACE }}")
  }

  @Test
  fun check() {
    val judgement = Judgement(
      listOf(
        Card(rank = Rank.QUEEN, suit = Suit.SPADE),
        Card(rank = Rank.TEN, suit = Suit.SPADE),
        Card(rank = Rank.FIVE, suit = Suit.CLUB),
        Card(rank = Rank.KING, suit = Suit.DIAMOND),
        Card(rank = Rank.TWO, suit = Suit.CLUB),
        Card(rank = Rank.THREE, suit = Suit.HEART),
        Card(rank = Rank.NINE, suit = Suit.HEART)
      )
    )
    val hand = judgement.judge()
    println(hand)
  }
}
