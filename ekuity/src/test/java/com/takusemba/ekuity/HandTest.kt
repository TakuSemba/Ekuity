package com.takusemba.ekuity

import com.google.common.truth.Truth.assertThat
import com.takusemba.ekuity.Hand.Flush
import com.takusemba.ekuity.Hand.FullHouse
import com.takusemba.ekuity.Hand.HighCard
import com.takusemba.ekuity.Hand.OnePair
import com.takusemba.ekuity.Hand.Quads
import com.takusemba.ekuity.Hand.Straight
import com.takusemba.ekuity.Hand.StraightFlush
import com.takusemba.ekuity.Hand.Trips
import com.takusemba.ekuity.Hand.TwoPair
import com.takusemba.ekuity.Rank.ACE
import com.takusemba.ekuity.Rank.EIGHT
import com.takusemba.ekuity.Rank.FIVE
import com.takusemba.ekuity.Rank.FOUR
import com.takusemba.ekuity.Rank.JACK
import com.takusemba.ekuity.Rank.KING
import com.takusemba.ekuity.Rank.NINE
import com.takusemba.ekuity.Rank.QUEEN
import com.takusemba.ekuity.Rank.SEVEN
import com.takusemba.ekuity.Rank.SIX
import com.takusemba.ekuity.Rank.TEN
import com.takusemba.ekuity.Rank.THREE
import com.takusemba.ekuity.Rank.TWO
import com.takusemba.ekuity.Suit.CLUB
import com.takusemba.ekuity.Suit.DIAMOND
import com.takusemba.ekuity.Suit.HEART
import com.takusemba.ekuity.Suit.SPADE
import com.takusemba.ekuity.util.Quadruple
import com.takusemba.ekuity.util.Quintuple
import org.junit.Test

class HandTest {

  @Test
  fun compareHands() {
    val highCard = HighCard(
      firstKicker = Card(ACE, CLUB),
      secondKicker = Card(KING, DIAMOND),
      thirdKicker = Card(QUEEN, HEART),
      forthKicker = Card(JACK, DIAMOND),
      fifthKicker = Card(SEVEN, DIAMOND)
    )
    val onePair = OnePair(
      pair = Pair(Card(FOUR, CLUB), Card(FOUR, HEART)),
      firstKicker = Card(QUEEN, DIAMOND),
      secondKicker = Card(TEN, CLUB),
      thirdKicker = Card(EIGHT, CLUB)
    )
    val twoPair = TwoPair(
      firstPair = Pair(Card(FIVE, HEART), Card(FIVE, SPADE)),
      secondPair = Pair(Card(TWO, SPADE), Card(TWO, HEART)),
      kicker = Card(QUEEN, DIAMOND)
    )
    val trips = Trips(
      triple = Triple(
        Card(FOUR, SPADE),
        Card(FOUR, CLUB),
        Card(FOUR, HEART)
      ),
      firstKicker = Card(ACE, CLUB),
      secondKicker = Card(KING, SPADE)
    )
    val straight = Straight(
      quintuple = Quintuple(
        Card(SIX, SPADE),
        Card(FIVE, HEART),
        Card(FOUR, CLUB),
        Card(THREE, HEART),
        Card(TWO, HEART)
      )
    )
    val flush = Flush(
      quintuple = Quintuple(
        Card(ACE, CLUB),
        Card(QUEEN, CLUB),
        Card(JACK, CLUB),
        Card(SIX, CLUB),
        Card(THREE, CLUB)
      )
    )
    val fullHouse = FullHouse(
      triple = Triple(
        Card(THREE, DIAMOND),
        Card(THREE, CLUB),
        Card(THREE, SPADE)
      ),
      pair = Pair(Card(NINE, CLUB), Card(NINE, HEART))
    )
    val quads = Quads(
      quadruple = Quadruple(
        Card(rank = KING, suit = HEART),
        Card(rank = KING, suit = DIAMOND),
        Card(rank = KING, suit = CLUB),
        Card(rank = KING, suit = SPADE)
      ),
      kicker = Card(rank = ACE, suit = DIAMOND)
    )
    val straightFlush = StraightFlush(
      quintuple = Quintuple(
        Card(SEVEN, CLUB),
        Card(SIX, CLUB),
        Card(FIVE, CLUB),
        Card(FOUR, CLUB),
        Card(THREE, CLUB)
      )
    )

    assertThat<Hand>(highCard).isLessThan(onePair)
    assertThat<Hand>(highCard).isLessThan(twoPair)
    assertThat<Hand>(highCard).isLessThan(trips)
    assertThat<Hand>(highCard).isLessThan(straight)
    assertThat<Hand>(highCard).isLessThan(flush)
    assertThat<Hand>(highCard).isLessThan(fullHouse)
    assertThat<Hand>(highCard).isLessThan(quads)
    assertThat<Hand>(highCard).isLessThan(straightFlush)

    assertThat<Hand>(onePair).isGreaterThan(highCard)
    assertThat<Hand>(onePair).isLessThan(twoPair)
    assertThat<Hand>(onePair).isLessThan(trips)
    assertThat<Hand>(onePair).isLessThan(straight)
    assertThat<Hand>(onePair).isLessThan(flush)
    assertThat<Hand>(onePair).isLessThan(fullHouse)
    assertThat<Hand>(onePair).isLessThan(quads)
    assertThat<Hand>(onePair).isLessThan(straightFlush)

    assertThat<Hand>(twoPair).isGreaterThan(highCard)
    assertThat<Hand>(twoPair).isGreaterThan(onePair)
    assertThat<Hand>(twoPair).isLessThan(trips)
    assertThat<Hand>(twoPair).isLessThan(straight)
    assertThat<Hand>(twoPair).isLessThan(flush)
    assertThat<Hand>(twoPair).isLessThan(fullHouse)
    assertThat<Hand>(twoPair).isLessThan(quads)
    assertThat<Hand>(twoPair).isLessThan(straightFlush)

    assertThat<Hand>(trips).isGreaterThan(highCard)
    assertThat<Hand>(trips).isGreaterThan(onePair)
    assertThat<Hand>(trips).isGreaterThan(twoPair)
    assertThat<Hand>(trips).isLessThan(straight)
    assertThat<Hand>(trips).isLessThan(flush)
    assertThat<Hand>(trips).isLessThan(fullHouse)
    assertThat<Hand>(trips).isLessThan(quads)
    assertThat<Hand>(trips).isLessThan(straightFlush)

    assertThat<Hand>(straight).isGreaterThan(highCard)
    assertThat<Hand>(straight).isGreaterThan(onePair)
    assertThat<Hand>(straight).isGreaterThan(twoPair)
    assertThat<Hand>(straight).isGreaterThan(trips)
    assertThat<Hand>(straight).isLessThan(flush)
    assertThat<Hand>(straight).isLessThan(fullHouse)
    assertThat<Hand>(straight).isLessThan(quads)
    assertThat<Hand>(straight).isLessThan(straightFlush)

    assertThat<Hand>(flush).isGreaterThan(highCard)
    assertThat<Hand>(flush).isGreaterThan(onePair)
    assertThat<Hand>(flush).isGreaterThan(twoPair)
    assertThat<Hand>(flush).isGreaterThan(trips)
    assertThat<Hand>(flush).isGreaterThan(straight)
    assertThat<Hand>(flush).isLessThan(fullHouse)
    assertThat<Hand>(flush).isLessThan(quads)
    assertThat<Hand>(flush).isLessThan(straightFlush)

    assertThat<Hand>(fullHouse).isGreaterThan(highCard)
    assertThat<Hand>(fullHouse).isGreaterThan(onePair)
    assertThat<Hand>(fullHouse).isGreaterThan(twoPair)
    assertThat<Hand>(fullHouse).isGreaterThan(trips)
    assertThat<Hand>(fullHouse).isGreaterThan(straight)
    assertThat<Hand>(fullHouse).isGreaterThan(flush)
    assertThat<Hand>(fullHouse).isLessThan(quads)
    assertThat<Hand>(fullHouse).isLessThan(straightFlush)

    assertThat<Hand>(quads).isGreaterThan(highCard)
    assertThat<Hand>(quads).isGreaterThan(onePair)
    assertThat<Hand>(quads).isGreaterThan(twoPair)
    assertThat<Hand>(quads).isGreaterThan(trips)
    assertThat<Hand>(quads).isGreaterThan(straight)
    assertThat<Hand>(quads).isGreaterThan(flush)
    assertThat<Hand>(quads).isGreaterThan(fullHouse)
    assertThat<Hand>(quads).isLessThan(straightFlush)

    assertThat<Hand>(straightFlush).isGreaterThan(highCard)
    assertThat<Hand>(straightFlush).isGreaterThan(onePair)
    assertThat<Hand>(straightFlush).isGreaterThan(twoPair)
    assertThat<Hand>(straightFlush).isGreaterThan(trips)
    assertThat<Hand>(straightFlush).isGreaterThan(straight)
    assertThat<Hand>(straightFlush).isGreaterThan(flush)
    assertThat<Hand>(straightFlush).isGreaterThan(fullHouse)
    assertThat<Hand>(straightFlush).isGreaterThan(quads)
  }

  @Test
  fun compareHighCard() {
    val left = HighCard(
      firstKicker = Card(ACE, CLUB),
      secondKicker = Card(KING, DIAMOND),
      thirdKicker = Card(QUEEN, HEART),
      forthKicker = Card(JACK, DIAMOND),
      fifthKicker = Card(SEVEN, DIAMOND)
    )
    val right = HighCard(
      firstKicker = Card(KING, CLUB),
      secondKicker = Card(QUEEN, DIAMOND),
      thirdKicker = Card(JACK, HEART),
      forthKicker = Card(SEVEN, DIAMOND),
      fifthKicker = Card(TWO, DIAMOND)
    )
    assertThat(left).isGreaterThan(right)
  }

  @Test
  fun compareOnePair() {
    val left = OnePair(
      pair = Pair(Card(KING, CLUB), Card(KING, HEART)),
      firstKicker = Card(QUEEN, DIAMOND),
      secondKicker = Card(TEN, CLUB),
      thirdKicker = Card(EIGHT, CLUB)
    )
    val right = OnePair(
      pair = Pair(Card(FOUR, CLUB), Card(FOUR, HEART)),
      firstKicker = Card(QUEEN, DIAMOND),
      secondKicker = Card(TEN, CLUB),
      thirdKicker = Card(EIGHT, CLUB)
    )
    assertThat(left).isGreaterThan(right)
  }

  @Test
  fun compareTwoPair() {
    val left = TwoPair(
      firstPair = Pair(Card(SIX, HEART), Card(SIX, SPADE)),
      secondPair = Pair(Card(TWO, SPADE), Card(TWO, HEART)),
      kicker = Card(QUEEN, DIAMOND)
    )
    val right = TwoPair(
      firstPair = Pair(Card(FIVE, HEART), Card(FIVE, SPADE)),
      secondPair = Pair(Card(TWO, SPADE), Card(TWO, HEART)),
      kicker = Card(QUEEN, DIAMOND)
    )
    assertThat(left).isGreaterThan(right)
  }

  @Test
  fun compareTrips() {
    val left = Trips(
      triple = Triple(
        Card(TEN, SPADE),
        Card(TEN, CLUB),
        Card(TEN, HEART)
      ),
      firstKicker = Card(ACE, CLUB),
      secondKicker = Card(KING, SPADE)
    )
    val right = Trips(
      triple = Triple(
        Card(FOUR, SPADE),
        Card(FOUR, CLUB),
        Card(FOUR, HEART)
      ),
      firstKicker = Card(ACE, CLUB),
      secondKicker = Card(KING, SPADE)
    )
    assertThat(left).isGreaterThan(right)
  }

  @Test
  fun compareStraight() {
    val left = Straight(
      quintuple = Quintuple(
        Card(SEVEN, HEART),
        Card(SIX, SPADE),
        Card(FIVE, HEART),
        Card(FOUR, CLUB),
        Card(THREE, HEART)
      )
    )
    val right = Straight(
      quintuple = Quintuple(
        Card(SIX, SPADE),
        Card(FIVE, HEART),
        Card(FOUR, CLUB),
        Card(THREE, HEART),
        Card(TWO, HEART)
      )
    )
    assertThat(left).isGreaterThan(right)
  }

  @Test
  fun compareFlush() {
    val left = Flush(
      quintuple = Quintuple(
        Card(ACE, CLUB),
        Card(QUEEN, CLUB),
        Card(JACK, CLUB),
        Card(SIX, CLUB),
        Card(THREE, CLUB)
      )
    )
    val right = Flush(
      quintuple = Quintuple(
        Card(QUEEN, CLUB),
        Card(JACK, CLUB),
        Card(SIX, CLUB),
        Card(FIVE, CLUB),
        Card(THREE, CLUB)
      )
    )
    assertThat(left).isGreaterThan(right)
  }

  @Test
  fun compareFullHouse() {
    val left = FullHouse(
      triple = Triple(
        Card(TEN, DIAMOND),
        Card(TEN, CLUB),
        Card(TEN, SPADE)
      ),
      pair = Pair(Card(NINE, CLUB), Card(NINE, HEART))
    )
    val right = FullHouse(
      triple = Triple(
        Card(THREE, DIAMOND),
        Card(THREE, CLUB),
        Card(THREE, SPADE)
      ),
      pair = Pair(Card(NINE, CLUB), Card(NINE, HEART))
    )
    assertThat(left).isGreaterThan(right)
  }

  @Test
  fun compareQuads() {
    val left = Quads(
      quadruple = Quadruple(
        Card(rank = KING, suit = HEART),
        Card(rank = KING, suit = DIAMOND),
        Card(rank = KING, suit = CLUB),
        Card(rank = KING, suit = SPADE)
      ),
      kicker = Card(rank = ACE, suit = DIAMOND)
    )
    val right = Quads(
      quadruple = Quadruple(
        Card(rank = JACK, suit = HEART),
        Card(rank = JACK, suit = DIAMOND),
        Card(rank = JACK, suit = CLUB),
        Card(rank = JACK, suit = SPADE)
      ),
      kicker = Card(rank = ACE, suit = DIAMOND)
    )
    assertThat(left).isGreaterThan(right)
  }

  @Test
  fun compareStraightFlush() {
    val left = StraightFlush(
      quintuple = Quintuple(
        Card(EIGHT, CLUB),
        Card(SEVEN, CLUB),
        Card(SIX, CLUB),
        Card(FIVE, CLUB),
        Card(FOUR, CLUB)
      )
    )
    val right = StraightFlush(
      quintuple = Quintuple(
        Card(SEVEN, CLUB),
        Card(SIX, CLUB),
        Card(FIVE, CLUB),
        Card(FOUR, CLUB),
        Card(THREE, CLUB)
      )
    )
    assertThat(left).isGreaterThan(right)
  }
}