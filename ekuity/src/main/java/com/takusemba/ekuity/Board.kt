package com.takusemba.ekuity

data class Board(private val _cards: MutableList<Card> = mutableListOf()) {

  init {
    require(_cards.isEmpty() || _cards.size == 3 || _cards.size == 4 || _cards.size == 5)
  }

  fun cards(): List<Card> {
    return _cards
  }

  fun flop(card: Card) {
    _cards += card
    require(_cards.size <= 5)
  }
}