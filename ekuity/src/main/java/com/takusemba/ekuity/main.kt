package com.takusemba.ekuity

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.options.versionOption
import com.takusemba.ekuity.Result.WIN

class Ekuity : CliktCommand() {

  init {
    versionOption("1.0.0")
  }

  val player by option("-p", "--player", "--players", help = "Sets players' cards")
    .split(Regex(","))
    .required()
    .validate { }

  val board by option("-b", "--board", help = "Sets board's cards")
    .required()
    .validate { }

  override fun run() {

    echo("board: $board")
    echo("player: $player")

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

    val history: MutableMap<Player, List<Pair<Result, Hand>>> = mutableMapOf()

    for (i in 0..1000) {
      val deckToPlay = deck.copy()
      val boardToPlay = board.copy()
      val game = Game(deckToPlay, listOf(player1, player2), boardToPlay)
      val result = game.play()
      for (player in result.keys) {
        history[player] = history.getOrDefault(player, mutableListOf()) + result.getValue(player)
      }
    }

    for (player in history.keys) {
      echo("player: $player")
      echo("wins: ${history.getValue(player).count { it.first == WIN }}")
    }
  }
}

fun main(args: Array<String>) = Ekuity().main(args)

