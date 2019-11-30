package com.takusemba.ekuity

import com.github.ajalt.clikt.core.CliktCommand
import com.takusemba.ekuity.Result.Settlement
import com.takusemba.ekuity.Result.Tie

class Ekuity : CliktCommand() {

  override fun run() {
    var tieCount = 0
    val map: MutableMap<Player, Int> = mutableMapOf()

    val deck = Deck()
    val player1 = Player(deck.draw(), deck.draw())
    val player2 = Player(deck.draw(), deck.draw())
    val board = Board(mutableListOf(deck.draw(), deck.draw(), deck.draw()))

    for (i in 0..1000) {
      val deckToPlay = deck.copy()
      val boardToPlay = board.copy()
      val game = Game(deckToPlay, listOf(player1, player2), boardToPlay)
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

