package com.takusemba.ekuity

class Board(vararg initialCards: Card) {

  private val cards: MutableList<Card> = mutableListOf()

  init {
    cards.addAll(initialCards)
  }

  fun cards(): List<Card> {
    return cards
  }

  fun flop(card: Card) {
    cards += card
    require(cards.size <= 5)
  }

  fun copy(): Board {
    return Board(*cards.toTypedArray())
  }
}
