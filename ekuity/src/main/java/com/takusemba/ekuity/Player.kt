package com.takusemba.ekuity

data class Player(val first: Card, val second: Card) {

  override fun toString(): String {
    return "$first $second"
  }
}