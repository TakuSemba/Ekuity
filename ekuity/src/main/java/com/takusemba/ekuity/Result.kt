package com.takusemba.ekuity

sealed class Result(open val hand: Hand) {

  data class Tie(override val hand: Hand) : Result(hand)

  data class Settlement(val winner: Player, override val hand: Hand) : Result(hand)
}