package com.takusemba.ekuity

data class Card(val rank: Rank, val suit: Suit) : Comparable<Card> {

  override fun compareTo(other: Card): Int {
    return rank.ordinal - other.rank.ordinal
  }

  companion object {

    fun of(value: String): Card {
      require(value.length == 2)
      return Card(Rank.of(value[0]), Suit.of(value[1]))
    }
  }
}