package com.takusemba.ekuity

class Board(vararg initialCards: Card) {

  private val cards: MutableList<Card> = mutableListOf()

  init {
    cards.addAll(initialCards)
  }

  enum class Stage {
    PRE_FLOP, FLOP, TURN, LIVER
  }

  fun state(): Stage {
    return when (cards.size) {
      0 -> Stage.PRE_FLOP
      3 -> Stage.FLOP
      4 -> Stage.TURN
      5 -> Stage.LIVER
      else -> throw IllegalStateException("Unknown state")
    }
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
