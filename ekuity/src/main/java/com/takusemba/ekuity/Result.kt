package com.takusemba.ekuity

sealed class Result(hand: Hand) {

  class Tie(val hand: Hand) : Result(hand)

  class Settlement(val winner: Player, hand: Hand) : Result(hand)
}