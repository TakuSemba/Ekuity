package com.takusemba.ekuity

enum class Rank {
  TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

  override fun toString(): String {
    return when (this) {
      TWO -> "2"
      THREE -> "3"
      FOUR -> "4"
      FIVE -> "5"
      SIX -> "6"
      SEVEN -> "7"
      EIGHT -> "8"
      NINE -> "9"
      TEN -> "T"
      JACK -> "J"
      QUEEN -> "Q"
      KING -> "K"
      ACE -> "A"
    }
  }

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