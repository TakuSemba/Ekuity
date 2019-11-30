package com.takusemba.ekuity

import com.github.ajalt.clikt.core.CliktCommand
import com.takusemba.ekuity.Result.Settlement
import com.takusemba.ekuity.Result.Tie

class Ekuity : CliktCommand() {

  override fun run() {
    val player1 = Player(Card(Rank.TEN, Suit.SPADE), Card(Rank.TEN, Suit.CLUB))
    val player2 = Player(Card(Rank.NINE, Suit.CLUB), Card(Rank.EIGHT, Suit.DIAMOND))
    val board = Board(
      mutableListOf(
        Card(Rank.ACE, Suit.SPADE),
        Card(Rank.SIX, Suit.CLUB),
        Card(Rank.SEVEN, Suit.DIAMOND)
      )
    )

    var tieCount = 0
    val map: MutableMap<Player, Int> = mutableMapOf()

    for (i in 0..1000) {
      val game = Game(listOf(player1, player2), board)
      when (val result = game.play()) {
        is Settlement -> {
          map[result.winner] = map.getOrDefault(result.winner, 0) + 1
        }
        is Tie -> {
          tieCount += 1
        }
      }
    }

    echo("player1: ${checkNotNull(map[player1]).toFloat() / 1000f}")
    echo("player2: ${checkNotNull(map[player2]).toFloat() / 1000f}")
  }
}

fun main(args: Array<String>) = Ekuity().main(args)

