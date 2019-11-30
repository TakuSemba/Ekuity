package com.takusemba.ekuity

class Deck(private val cards: MutableList<Card> = fullDeck()) {

  fun draw(): Card {
    val card = cards.random()
    if (cards.contains(card)) {
      cards.remove(card)
    }
    return card
  }

  fun copy(): Deck {
    return Deck(cards.toMutableList())
  }

  companion object {

    fun fullDeck(): MutableList<Card> {
      return MutableList(52) { index ->
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
    }
  }
}