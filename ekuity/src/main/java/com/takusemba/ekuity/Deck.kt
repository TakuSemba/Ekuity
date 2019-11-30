package com.takusemba.ekuity

data class Deck(
  private val _cards: MutableList<Card> = MutableList(52) { index ->
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

  init {
    require(_cards.count { it.rank == Rank.TWO } == 4)
    require(_cards.count { it.rank == Rank.THREE } == 4)
    require(_cards.count { it.rank == Rank.FOUR } == 4)
    require(_cards.count { it.rank == Rank.FIVE } == 4)
    require(_cards.count { it.rank == Rank.SIX } == 4)
    require(_cards.count { it.rank == Rank.SEVEN } == 4)
    require(_cards.count { it.rank == Rank.EIGHT } == 4)
    require(_cards.count { it.rank == Rank.NINE } == 4)
    require(_cards.count { it.rank == Rank.TEN } == 4)
    require(_cards.count { it.rank == Rank.JACK } == 4)
    require(_cards.count { it.rank == Rank.QUEEN } == 4)
    require(_cards.count { it.rank == Rank.KING } == 4)
    require(_cards.count { it.rank == Rank.ACE } == 4)
    require(_cards.count { it.suit == Suit.CLUB } == 13)
    require(_cards.count { it.suit == Suit.HEART } == 13)
    require(_cards.count { it.suit == Suit.DIAMOND } == 13)
    require(_cards.count { it.suit == Suit.SPADE } == 13)
  }

  fun cards(): List<Card> {
    return _cards
  }

  fun draw(): Card {
    val card = _cards.random()
    if (_cards.contains(card)) {
      _cards.remove(card)
    } else {
      throw IllegalStateException("$card is not found in deck")
    }
    return card
  }
}