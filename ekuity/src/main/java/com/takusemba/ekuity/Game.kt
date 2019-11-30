package com.takusemba.ekuity

import com.takusemba.ekuity.Result.Settlement
import com.takusemba.ekuity.Result.Tie

class Game(private val deck: Deck, private val players: List<Player>, private val board: Board) {

  init {
    require(players.size < 10)
  }

  fun play(): Result {
    repeat(5 - board.cards().size) {
      board.flop(deck.draw())
    }

    val map: MutableMap<Hand, MutableSet<Player>> = mutableMapOf()
    for (player in players) {
      val judgement = Judgement(listOf(player.first, player.second) + board.cards())
      val hand = judgement.judge()
      val players = map.getOrDefault(hand, mutableSetOf())
      players.add(player)
      map[hand] = players
    }

    val sorted = map.toSortedMap(Comparator { a, b -> b.compareTo(a) })
    val players = checkNotNull(sorted[sorted.firstKey()])

    return if (players.size >= 2) {
      Tie(sorted.firstKey())
    } else {
      Settlement(players.first(), sorted.firstKey())
    }
  }
}