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
import org.junit.Test

class JudgementTest {

  @Test
  fun judgeHighCard() {
    val cards = listOf(
      Card(ACE, CLUB),
      Card(KING, DIAMOND),
      Card(QUEEN, HEART),
      Card(JACK, DIAMOND),
      Card(SEVEN, DIAMOND),
      Card(THREE, SPADE),
      Card(TWO, SPADE)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(HighCard::class.java)
  }

  @Test
  fun judgeOnePair() {
    val cards = listOf(
      Card(ACE, CLUB),
      Card(ACE, DIAMOND),
      Card(QUEEN, HEART),
      Card(JACK, DIAMOND),
      Card(SEVEN, DIAMOND),
      Card(THREE, SPADE),
      Card(TWO, SPADE)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(OnePair::class.java)
  }

  @Test
  fun judgeTwoPair() {
    val cards = listOf(
      Card(ACE, CLUB),
      Card(ACE, DIAMOND),
      Card(QUEEN, HEART),
      Card(QUEEN, DIAMOND),
      Card(SEVEN, DIAMOND),
      Card(THREE, SPADE),
      Card(TWO, SPADE)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(TwoPair::class.java)
  }

  @Test
  fun judgeTrips() {
    val cards = listOf(
      Card(ACE, CLUB),
      Card(ACE, DIAMOND),
      Card(ACE, HEART),
      Card(QUEEN, DIAMOND),
      Card(SEVEN, DIAMOND),
      Card(THREE, SPADE),
      Card(TWO, SPADE)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(Trips::class.java)
  }

  @Test
  fun judgeStraight() {
    val cards = listOf(
      Card(KING, DIAMOND),
      Card(JACK, DIAMOND),
      Card(SIX, SPADE),
      Card(FIVE, HEART),
      Card(FOUR, CLUB),
      Card(THREE, HEART),
      Card(TWO, HEART)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(Straight::class.java)
  }

  @Test
  fun judgeFlush() {
    val cards = listOf(
      Card(KING, HEART),
      Card(JACK, HEART),
      Card(NINE, HEART),
      Card(SIX, SPADE),
      Card(FOUR, CLUB),
      Card(THREE, HEART),
      Card(TWO, HEART)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(Flush::class.java)
  }

  @Test
  fun judgeFullHouse() {
    val cards = listOf(
      Card(ACE, CLUB),
      Card(ACE, DIAMOND),
      Card(ACE, HEART),
      Card(QUEEN, DIAMOND),
      Card(QUEEN, CLUB),
      Card(THREE, SPADE),
      Card(TWO, SPADE)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(FullHouse::class.java)
  }

  @Test
  fun judgeQuads() {
    val cards = listOf(
      Card(ACE, CLUB),
      Card(ACE, DIAMOND),
      Card(ACE, HEART),
      Card(ACE, SPADE),
      Card(QUEEN, CLUB),
      Card(THREE, SPADE),
      Card(TWO, SPADE)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(Quads::class.java)
  }

  @Test
  fun judgeStraightFlush() {
    val cards = listOf(
      Card(TEN, SPADE),
      Card(SEVEN, CLUB),
      Card(SIX, CLUB),
      Card(FIVE, CLUB),
      Card(FOUR, CLUB),
      Card(THREE, CLUB),
      Card(TWO, SPADE)
    )
    val judgement = Judgement(cards)
    val hand = judgement.judge()
    assertThat(hand).isInstanceOf(StraightFlush::class.java)
  }
}
