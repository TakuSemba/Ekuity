package com.takusemba.ekuity

data class Board(private val seed: List<Card>) {

  private val flops = mutableListOf<Card>()

  val cards: List<Card>
    get() = seed + flops

  init {
    require(seed.isEmpty() || seed.size == 3 || seed.size == 4 || seed.size == 5)
  }

  fun flop(card: Card) {
    flops += card
    require(cards.size <= 5)
  }

  fun clear() {
    flops.clear()
  }
}