package com.takusemba.ekuity

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.int
import com.jakewharton.picnic.BorderStyle
import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.table
import com.takusemba.ekuity.Hand.Flush
import com.takusemba.ekuity.Hand.FullHouse
import com.takusemba.ekuity.Hand.HighCard
import com.takusemba.ekuity.Hand.OnePair
import com.takusemba.ekuity.Hand.Quads
import com.takusemba.ekuity.Hand.Straight
import com.takusemba.ekuity.Hand.StraightFlush
import com.takusemba.ekuity.Hand.Trips
import com.takusemba.ekuity.Hand.TwoPair
import com.takusemba.ekuity.Result.LOSE
import com.takusemba.ekuity.Result.TIE
import com.takusemba.ekuity.Result.WIN
import kotlin.system.measureTimeMillis

class Ekuity : CliktCommand() {

  init {
    versionOption("1.0.0")
  }

  val players: List<String> by option("-p", "--player", "--players", help = "Set players' cards")
    .split(Regex(","))
    .required()

  val board: String? by option("-b", "--board", help = "Set board's cards")

  val exposed: List<String>? by option("-e", "--exposed", help = "Set exposed cards")
    .split(Regex(","))

  val iterations: Int by option("-i", "--iterations", help = "Set iteration count")
    .int()
    .default(1000)

  val verbose: Boolean by option("-v", "--verbose", help = "Show hand possibility detail")
    .flag(default = false)

  override fun run() {
    val deck = Deck()

    val playerCards = players.map { Pair(Card.of(it.substring(0, 2)), Card.of(it.substring(2, 4))) }
    val players = playerCards.map { Player(it.first, it.second) }

    val boardCards = board?.windowed(2, 2)?.map { Card.of(it) }?.toTypedArray() ?: emptyArray()
    val board = Board(*boardCards)

    val exposedCards = exposed?.map { Card.of(it) } ?: emptyList()

    deck.remove(playerCards.flatMap { listOf(it.first, it.second) })
    deck.remove(*boardCards)
    deck.remove(exposedCards)

    val history: MutableMap<Player, List<Pair<Result, Hand>>> = mutableMapOf()
    val time = measureTimeMillis {
      for (i in 0..iterations) {
        val deckToPlay = deck.copy()
        val boardToPlay = board.copy()
        val game = Game(deckToPlay, players, boardToPlay)
        val result = game.play()
        for (player in result.keys) {
          history[player] = history.getOrDefault(player, mutableListOf()) + result.getValue(player)
        }
      }
    }

    echo("")

    val result = table {
      style {
        borderStyle = BorderStyle.Hidden
      }
      cellStyle {
        alignment = TextAlignment.MiddleCenter
        paddingLeft = 1
        paddingRight = 1
        borderLeft = true
        borderRight = true
      }
      header {
        cellStyle {
          border = true
          alignment = TextAlignment.MiddleCenter
        }
        row("player", "win", "lose", "tie")
      }
      body {
        for (player in history.keys) {
          val win = history.getValue(player).count { it.first == WIN } / iterations.toFloat()
          val lose = history.getValue(player).count { it.first == LOSE } / iterations.toFloat()
          val tie = history.getValue(player).count { it.first == TIE } / iterations.toFloat()
          row("$player", "$win", "$lose", "$tie")
        }
      }
    }
    echo(result.toString())

    if (verbose) {
      val handPossibilities = table {
        style {
          borderStyle = BorderStyle.Hidden
        }
        cellStyle {
          alignment = TextAlignment.MiddleCenter
          paddingLeft = 1
          paddingRight = 1
          borderLeft = true
          borderRight = true
        }
        header {
          cellStyle {
            border = true
            alignment = TextAlignment.MiddleCenter
          }
          row("", *players.toTypedArray())
        }
        body {
          row {
            cell("high card") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is HighCard }.count() / iterations.toFloat())
            }
          }
          row {
            cell("one pair") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is OnePair }.count() / iterations.toFloat())
            }
          }
          row {
            cell("two pair") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is TwoPair }.count() / iterations.toFloat())
            }
          }
          row {
            cell("trips") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is Trips }.count() / iterations.toFloat())
            }
          }
          row {
            cell("straight") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is Straight }.count() / iterations.toFloat())
            }
          }
          row {
            cell("flush") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is Flush }.count() / iterations.toFloat())
            }
          }
          row {
            cell("full house") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is FullHouse }.count() / iterations.toFloat())
            }
          }
          row {
            cell("quads") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is Quads }.count() / iterations.toFloat())
            }
          }
          row {
            cell("straight flush") {
              alignment = TextAlignment.MiddleLeft
            }
            for (player in history.keys) {
              val handsHistory = history.getValue(player)
              cell(handsHistory.filter { it.second is StraightFlush }.count() / iterations.toFloat())
            }
          }
        }
      }
      echo(handPossibilities.toString())
    }

    echo("$iterations iterations in $time ms")
  }
}

fun main(args: Array<String>) = Ekuity().main(args)

