package com.takusemba.ekuity

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.int
import com.takusemba.ekuity.Result.WIN

class Ekuity : CliktCommand() {

  init {
    versionOption("1.0.0")
  }

  val players: List<String> by option("-p", "--player", "--players", help = "Sets players' cards")
    .split(Regex(","))
    .required()

  val board: String by option("-b", "--board", help = "Sets board's cards")
    .required()

  val exposed: List<String>? by option("-e", "--exposed", help = "Sets exposed cards")
    .split(Regex(","))

  val iterator: Int by option("-i", "--iterations", help = "Sets iteration count")
    .int()
    .default(1000)

  override fun run() {
    val deck = Deck()

    val playerCards = players.map { Pair(Card.of(it.substring(0, 2)), Card.of(it.substring(2, 4))) }
    val players = playerCards.map { Player(it.first, it.second) }

    val boardCards = board.windowed(size = 2, step = 2).map { Card.of(it) }.toTypedArray()
    val board = Board(*boardCards)

    val exposedCards = exposed?.map { Card.of(it) } ?: emptyList()

    deck.remove(playerCards.flatMap { listOf(it.first, it.second) })
    deck.remove(*boardCards)
    deck.remove(exposedCards)

    val history: MutableMap<Player, List<Pair<Result, Hand>>> = mutableMapOf()
    for (i in 0..iterator) {
      val deckToPlay = deck.copy()
      val boardToPlay = board.copy()
      val game = Game(deckToPlay, players, boardToPlay)
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

