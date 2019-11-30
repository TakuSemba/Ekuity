package com.takusemba.ekuity

enum class Suit {
  CLUB, DIAMOND, HEART, SPADE;

  override fun toString(): String {
    return when (this) {
      CLUB -> "c"
      DIAMOND -> "d"
      HEART -> "h"
      SPADE -> "s"
    }
  }

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