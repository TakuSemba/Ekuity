package com.takusemba.ekuity

import com.takusemba.ekuity.Board.Stage

class Game(private val deck: Deck, private val players: List<Player>, private val board: Board) {

  init {
    require(players.size < 10)
  }

  fun play(): Map<Player, Pair<Result, Hand>> {
    val draws = when (board.state()) {
      Stage.PRE_FLOP -> 5
      Stage.FLOP -> 2
      Stage.TURN -> 1
      Stage.LIVER -> 0
    }
    repeat(draws) {
      board.flop(deck.draw())
    }

    val handMap: MutableMap<Hand, Set<Player>> = mutableMapOf()
    val playerMap: MutableMap<Player, Hand> = mutableMapOf()
    for (player in players) {
      val judgement = Judgement(listOf(player.first, player.second) + board.cards())
      val hand = judgement.judge()
      handMap[hand] = handMap.getOrDefault(hand, mutableSetOf()) + player
      playerMap[player] = hand
    }

    val sortedHandMap = handMap.toSortedMap(Comparator { a, b -> b.compareTo(a) })
    val winners = checkNotNull(sortedHandMap[sortedHandMap.firstKey()])

    val isTie = winners.size == players.size

    val result: MutableMap<Player, Pair<Result, Hand>> = mutableMapOf()
    for (player in players) {
      if (isTie) {
        result[player] = Pair(Result.TIE, playerMap.getValue(player))
      } else {
        result[player] = if (winners.contains(player)) {
          Pair(Result.WIN, playerMap.getValue(player))
        } else {
          Pair(Result.LOSE, playerMap.getValue(player))
        }
      }
    }
    return result
  }
}