package com.takusemba.ekuity

enum class Suit {
  CLUB, DIAMOND, HEART, SPADE;

  companion object {

    fun of(suit: Char): Suit = when (suit) {
      'c' -> CLUB
      'd' -> DIAMOND
      'h' -> HEART
      's' -> SPADE
      else -> throw IllegalStateException("Unknown suit")
    }
  }
}