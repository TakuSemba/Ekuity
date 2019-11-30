package com.takusemba.ekuity

import com.takusemba.ekuity.Result.Settlement
import com.takusemba.ekuity.Result.Tie

class Game(private val players: List<Player>, private val board: Board) {

  private val deck: Deck = Deck().apply {
    for (player in players) {
      remove(player.first)
      remove(player.second)
    }
    for (card in board.cards) {
      remove(card)
    }
  }

  init {
    require(players.size < 10)
  }

  fun play(): Result {
    repeat(5 - board.cards.size) {
      board.flop(deck.draw())
    }

    val map: MutableMap<Hand, MutableSet<Player>> = mutableMapOf()
    for (player in players) {
      val judgement = Judgement(listOf(player.first, player.second) + board.cards)
      val hand = judgement.judge()
      println(hand)
      val players = map.getOrDefault(hand, mutableSetOf())
      players.add(player)
      map[hand] = players
    }

    board.clear()

    val sorted = map.toSortedMap(Comparator { a, b -> b.compareTo(a) })

    println(sorted.toString())

    val players = checkNotNull(sorted[sorted.firstKey()])

    return if (players.size >= 2) {
      Tie(sorted.firstKey())
    } else {
      Settlement(players.first(), sorted.firstKey())
    }
  }
}