package com.takusemba.ekuity

data class Board(val cards: MutableList<Card> = mutableListOf()) {

  init {
    require(cards.isEmpty() || cards.size == 3 || cards.size == 4 || cards.size == 5)
  }

  fun flop(card: Card) {
    cards += card
    require(cards.size <= 5)
  }
}