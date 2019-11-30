package com.takusemba.ekuity

data class Deck(
  private val cards: MutableList<Card> = MutableList(52) { index ->
    val rank = when (index % 13) {
      0 -> Rank.TWO
      1 -> Rank.THREE
      2 -> Rank.FOUR
      3 -> Rank.FIVE
      4 -> Rank.SIX
      5 -> Rank.SEVEN
      6 -> Rank.EIGHT
      7 -> Rank.NINE
      8 -> Rank.TEN
      9 -> Rank.JACK
      10 -> Rank.QUEEN
      11 -> Rank.KING
      12 -> Rank.ACE
      else -> throw IllegalStateException("unknown rank")
    }
    val suit = when (index % 4) {
      0 -> Suit.CLUB
      1 -> Suit.DIAMOND
      2 -> Suit.HEART
      3 -> Suit.SPADE
      else -> throw IllegalStateException("unknown suit")
    }
    Card(rank, suit)
  }
) {

  fun draw(): Card {
    val card = cards.random()
    if (cards.contains(card)) {
      cards.remove(card)
    } else {
      throw IllegalStateException("$card is not found in deck")
    }
    return card
  }
}