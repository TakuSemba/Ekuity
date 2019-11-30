package com.takusemba.ekuity

import com.takusemba.ekuity.Hand.Flush
import com.takusemba.ekuity.Hand.FullHouse
import com.takusemba.ekuity.Hand.HighCard
import com.takusemba.ekuity.Hand.OnePair
import com.takusemba.ekuity.Hand.Quads
import com.takusemba.ekuity.Hand.Straight
import com.takusemba.ekuity.Hand.StraightFlush
import com.takusemba.ekuity.Hand.Trips
import com.takusemba.ekuity.Hand.TwoPair
import com.takusemba.ekuity.util.Quadruple
import com.takusemba.ekuity.util.Quintuple

class Judgement(private val cards: List<Card>) {

  init {
    require(cards.distinct().size == 7)
    require(cards.size == 7)
  }

  fun judge(): Hand {

    val sorted = cards.sortedDescending()

    // straight flush
    val straightFlush = checkStraightFlush(sorted)
    if (straightFlush != null) {
      return straightFlush
    }

    // quads
    val quads = checkQuads(sorted)
    if (quads != null) {
      return quads
    }

    // full house
    val fullHouse = checkFullHouse(sorted)
    if (fullHouse != null) {
      return fullHouse
    }

    // flush
    val flush = checkFlush(sorted)
    if (flush != null) {
      return flush
    }

    // straight
    val straight = checkStraight(sorted)
    if (straight != null) {
      return straight
    }

    // trips
    val trips = checkTrips(sorted)
    if (trips != null) {
      return trips
    }

    // two pair
    val twoPair = checkTwoPair(sorted)
    if (twoPair != null) {
      return twoPair
    }

    // one pair
    val onePair = checkOnePair(sorted)
    if (onePair != null) {
      return onePair
    }

    // high card
    return checkHighCard(sorted)
  }

  private fun checkStraightFlush(cards: List<Card>): StraightFlush? {
    val groupBySuit = cards.groupBy { it.suit }
    val maxSameSuit = checkNotNull(groupBySuit.maxBy { it.value.size })
    if (maxSameSuit.value.size >= 5) {
      for (card in maxSameSuit.value) {
        val index = maxSameSuit.value.indexOf(card)
        if (cards.size - index >= 5) {
          if (
            cards[index].rank.ordinal - 1 == cards[index + 1].rank.ordinal &&
            cards[index + 1].rank.ordinal - 1 == cards[index + 2].rank.ordinal &&
            cards[index + 2].rank.ordinal - 1 == cards[index + 3].rank.ordinal &&
            cards[index + 3].rank.ordinal - 1 == cards[index + 4].rank.ordinal
          ) {
            val straightFlush = cards.subList(index, index + 5)
            return StraightFlush(
              Quintuple(
                straightFlush[0],
                straightFlush[1],
                straightFlush[2],
                straightFlush[3],
                straightFlush[4]
              )
            )
          }
        }
      }
      if (
        cards.any { it.rank == Rank.ACE } &&
        cards.any { it.rank == Rank.TWO } &&
        cards.any { it.rank == Rank.THREE } &&
        cards.any { it.rank == Rank.FOUR } &&
        cards.any { it.rank == Rank.FIVE }
      ) {
        return StraightFlush(
          Quintuple(
            checkNotNull(cards.find { it.rank == Rank.ACE }),
            checkNotNull(cards.find { it.rank == Rank.TWO }),
            checkNotNull(cards.find { it.rank == Rank.THREE }),
            checkNotNull(cards.find { it.rank == Rank.FOUR }),
            checkNotNull(cards.find { it.rank == Rank.FIVE })
          )
        )
      }
    }
    return null
  }

  private fun checkQuads(cards: List<Card>): Quads? {
    val groupByRank = cards.groupBy { it.rank }.toSortedMap(Comparator { a, b -> b.compareTo(a) })
    val maxSameRank = checkNotNull(groupByRank.maxBy { it.value.size })
    if (maxSameRank.value.size == 4) {
      val kicker = checkNotNull(cards.minus(maxSameRank.value).max())
      return Quads(
        Quadruple(
          maxSameRank.value[0],
          maxSameRank.value[1],
          maxSameRank.value[2],
          maxSameRank.value[3]
        ),
        kicker
      )
    }
    return null
  }

  private fun checkFullHouse(cards: List<Card>): FullHouse? {
    val groupByRank = cards.groupBy { it.rank }.toSortedMap(Comparator { a, b -> b.compareTo(a) })
    val maxSameRank = checkNotNull(groupByRank.maxBy { it.value.size })
    if (maxSameRank.value.size == 3) {
      val remainGroupByRank = cards.minus(maxSameRank.value).groupBy { it.rank }
      val secondGroupByRank = remainGroupByRank.toSortedMap(Comparator { a, b -> b.compareTo(a) })
      val secondMaxSameRank = checkNotNull(secondGroupByRank.maxBy { it.value.size })
      if (secondMaxSameRank.value.size >= 2) {
        val secondSorted = secondMaxSameRank.value.sortedDescending()
        return FullHouse(
          Triple(
            maxSameRank.value[0],
            maxSameRank.value[1],
            maxSameRank.value[2]
          ),
          Pair(
            secondSorted[0],
            secondSorted[1]
          )
        )
      }
    }
    return null
  }

  private fun checkFlush(cards: List<Card>): Flush? {
    val groupBySuit = cards.groupBy { it.suit }
    val maxSameSuit = checkNotNull(groupBySuit.maxBy { it.value.size })
    if (maxSameSuit.value.size >= 5) {
      val sortedFlush = maxSameSuit.value.sortedDescending().take(5)
      return Flush(
        Quintuple(
          sortedFlush[0],
          sortedFlush[1],
          sortedFlush[2],
          sortedFlush[3],
          sortedFlush[4]
        )
      )
    }
    return null
  }

  private fun checkStraight(cards: List<Card>): Straight? {
    for (card in cards) {
      val index = cards.indexOf(card)
      if (cards.size - index >= 5) {
        if (
          cards[index].rank.ordinal - 1 == cards[index + 1].rank.ordinal &&
          cards[index + 1].rank.ordinal - 1 == cards[index + 2].rank.ordinal &&
          cards[index + 2].rank.ordinal - 1 == cards[index + 3].rank.ordinal &&
          cards[index + 3].rank.ordinal - 1 == cards[index + 4].rank.ordinal
        ) {
          val sortedStraight = cards.subList(index, index + 5)
          return Straight(
            Quintuple(
              sortedStraight[0],
              sortedStraight[1],
              sortedStraight[2],
              sortedStraight[3],
              sortedStraight[4]
            )
          )
        }
      }
    }
    if (
      cards.any { it.rank == Rank.ACE } &&
      cards.any { it.rank == Rank.TWO } &&
      cards.any { it.rank == Rank.THREE } &&
      cards.any { it.rank == Rank.FOUR } &&
      cards.any { it.rank == Rank.FIVE }
    ) {
      return Straight(
        Quintuple(
          checkNotNull(cards.find { it.rank == Rank.ACE }),
          checkNotNull(cards.find { it.rank == Rank.TWO }),
          checkNotNull(cards.find { it.rank == Rank.THREE }),
          checkNotNull(cards.find { it.rank == Rank.FOUR }),
          checkNotNull(cards.find { it.rank == Rank.FIVE })
        )
      )
    }
    return null
  }

  private fun checkTrips(cards: List<Card>): Trips? {
    val groupByRank = cards.groupBy { it.rank }.toSortedMap(Comparator { a, b -> b.compareTo(a) })
    val maxSameRank = checkNotNull(groupByRank.maxBy { it.value.size })
    if (maxSameRank.value.size == 3) {
      val remain = cards.minus(maxSameRank.value)
      val firstKicker = checkNotNull(remain.maxBy { it.rank })
      val secondKicker = checkNotNull(remain.minus(firstKicker).maxBy { it.rank })
      return Trips(
        Triple(maxSameRank.value[0], maxSameRank.value[1], maxSameRank.value[2]),
        firstKicker,
        secondKicker
      )
    }
    return null
  }

  private fun checkTwoPair(cards: List<Card>): TwoPair? {
    val groupByRank = cards.groupBy { it.rank }.toSortedMap(Comparator { a, b -> b.compareTo(a) })
    val maxSameRank = checkNotNull(groupByRank.maxBy { it.value.size })
    if (maxSameRank.value.size == 2) {
      val remainGroupByRank = cards.minus(maxSameRank.value).groupBy { it.rank }
      val secondGroupByRank = remainGroupByRank.toSortedMap(Comparator { a, b -> b.compareTo(a) })
      val secondMaxSameRank = checkNotNull(secondGroupByRank.maxBy { it.value.size })
      if (secondMaxSameRank.value.size == 2) {
        val kicker = checkNotNull(cards.minus(maxSameRank.value + secondMaxSameRank.value).max())
        return TwoPair(
          Pair(maxSameRank.value[0], maxSameRank.value[1]),
          Pair(secondMaxSameRank.value[0], secondMaxSameRank.value[1]),
          kicker
        )
      }
    }
    return null
  }

  private fun checkOnePair(cards: List<Card>): OnePair? {
    val groupByRank = cards.groupBy { it.rank }.toSortedMap(Comparator { a, b -> b.compareTo(a) })
    val maxSameRank = checkNotNull(groupByRank.maxBy { it.value.size })
    if (maxSameRank.value.size == 2) {
      val remain = cards.minus(maxSameRank.value).sortedDescending()
      val firstKicker = remain[0]
      val secondKicker = remain[1]
      val thirdKicker = remain[2]
      return OnePair(
        Pair(maxSameRank.value[0], maxSameRank.value[1]),
        firstKicker,
        secondKicker,
        thirdKicker
      )
    }
    return null
  }

  private fun checkHighCard(cards: List<Card>): HighCard {
    val firstKicker = cards[0]
    val secondKicker = cards[1]
    val thirdKicker = cards[2]
    val forthKicker = cards[3]
    val fifthKicker = cards[4]
    return HighCard(firstKicker, secondKicker, thirdKicker, forthKicker, fifthKicker)
  }
}