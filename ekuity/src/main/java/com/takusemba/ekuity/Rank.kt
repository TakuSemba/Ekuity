package com.takusemba.ekuity

enum class Rank {
  TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

  companion object {

    fun of(rank: Char): Rank = when (rank) {
      '2' -> TWO
      '3' -> THREE
      '4' -> FOUR
      '5' -> FIVE
      '6' -> SIX
      '7' -> SEVEN
      '8' -> EIGHT
      '9' -> NINE
      'T' -> TEN
      'J' -> JACK
      'Q' -> QUEEN
      'K' -> KING
      'A' -> ACE
      else -> throw IllegalStateException("Unknown rank")
    }
  }
}