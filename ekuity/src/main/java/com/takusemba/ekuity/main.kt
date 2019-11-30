package com.takusemba.ekuity

import com.github.ajalt.clikt.core.CliktCommand
import com.takusemba.ekuity.Result.Settlement
import com.takusemba.ekuity.Result.Tie

class Ekuity : CliktCommand() {

  override fun run() {
    var tieCount = 0
    val map: MutableMap<Player, Int> = mutableMapOf()

    val deck = Deck()

    val card1 = Card(Rank.NINE, Suit.DIAMOND)
    val card2 = Card(Rank.KING, Suit.HEART)
    val card3 = Card(Rank.NINE, Suit.CLUB)
    val card4 = Card(Rank.TWO, Suit.DIAMOND)

    val player1 = Player(card1, card2)
    val player2 = Player(card3, card4)

    deck.remove(card1)
    deck.remove(card2)
    deck.remove(card3)
    deck.remove(card4)

    echo("player1: $player1")
    echo("player2: $player2")

    val board = Board()

    for (i in 0..100000) {
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

