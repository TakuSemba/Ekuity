package com.takusemba.ekuity

data class Card(val rank: Rank, val suit: Suit) : Comparable<Card> {

  override fun compareTo(other: Card): Int {
    return rank.ordinal - other.rank.ordinal
  }
}