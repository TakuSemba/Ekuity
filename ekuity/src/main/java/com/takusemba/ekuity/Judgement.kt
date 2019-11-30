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

class Judgement(private val cards: List<Card>) {

  init {
    require(cards.size == 7)
  }

  fun judge(): Hand {

    val sorted = cards.sortedDescending()
    val groupBySuit = sorted.groupBy { it.suit }
    val groupByRank = sorted.groupBy { it.rank }.toSortedMap(REVERSE_COMPARATOR)
    val maxSameSuit = checkNotNull(groupBySuit.maxBy { it.value.size })
    val maxSameRank = checkNotNull(groupByRank.maxBy { it.value.size })

    // straight flush
    if (maxSameSuit.value.size >= 5) {
      for (card in maxSameSuit.value) {
        val index = maxSameSuit.value.indexOf(card)
        if (cards.size - index >= 5) {
          if (
            cards[index].rank.ordinal + 1 == cards[index + 1].rank.ordinal &&
            cards[index + 1].rank.ordinal + 1 == cards[index + 2].rank.ordinal &&
            cards[index + 2].rank.ordinal + 1 == cards[index + 3].rank.ordinal &&
            cards[index + 3].rank.ordinal + 1 == cards[index + 4].rank.ordinal
          ) {
            return StraightFlush(cards.subList(index, index + 5))
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
          listOf(
            checkNotNull(cards.find { it.rank == Rank.ACE }),
            checkNotNull(cards.find { it.rank == Rank.TWO }),
            checkNotNull(cards.find { it.rank == Rank.THREE }),
            checkNotNull(cards.find { it.rank == Rank.FOUR }),
            checkNotNull(cards.find { it.rank == Rank.FIVE })
          )
        )
      }
    }

    // quads
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

    // full house
    if (maxSameRank.value.size == 3) {
      val remain = cards.minus(maxSameRank.value)
      val secondGroupByRank = remain.groupBy { it.rank }.toSortedMap(REVERSE_COMPARATOR)
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

    // flush
    if (maxSameSuit.value.size >= 5) {
      maxSameSuit.value.sortedDescending().take(5)
      return Flush(maxSameSuit.value.sortedDescending().take(5))
    }

    // straight
    for (card in sorted) {
      val index = sorted.indexOf(card)
      if (cards.size - index >= 5) {
        if (
          cards[index].rank.ordinal + 1 == cards[index + 1].rank.ordinal &&
          cards[index + 1].rank.ordinal + 1 == cards[index + 2].rank.ordinal &&
          cards[index + 2].rank.ordinal + 1 == cards[index + 3].rank.ordinal &&
          cards[index + 3].rank.ordinal + 1 == cards[index + 4].rank.ordinal
        ) {
          return Straight(cards.subList(index, index + 5))
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
        listOf(
          checkNotNull(cards.find { it.rank == Rank.ACE }),
          checkNotNull(cards.find { it.rank == Rank.TWO }),
          checkNotNull(cards.find { it.rank == Rank.THREE }),
          checkNotNull(cards.find { it.rank == Rank.FOUR }),
          checkNotNull(cards.find { it.rank == Rank.FIVE })
        )
      )
    }

    // trips
    if (maxSameRank.value.size == 3) {
      val remain = cards.minus(maxSameRank.value)
      val firstKicker = checkNotNull(remain.maxBy { it.rank })
      val secondKicker = checkNotNull(remain.minus(firstKicker).maxBy { it.rank })
      println(maxSameRank)
      println(firstKicker)
      println(secondKicker)
      return Trips(
        Triple(maxSameRank.value[0], maxSameRank.value[1], maxSameRank.value[2]),
        firstKicker,
        secondKicker
      )
    }

    // two pair
    if (maxSameRank.value.size == 2) {
      val remain = cards.minus(maxSameRank.value)
      val secondGroupByRank = remain.groupBy { it.rank }.toSortedMap(REVERSE_COMPARATOR)
      val secondMaxSameRank = checkNotNull(secondGroupByRank.maxBy { it.value.size })
      if (secondMaxSameRank.value.size == 2) {
        val kicker = checkNotNull(remain.minus(secondMaxSameRank.value).max())
        return TwoPair(
          Pair(maxSameRank.value[0], maxSameRank.value[1]),
          Pair(secondMaxSameRank.value[0], secondMaxSameRank.value[1]),
          kicker
        )
      }
    }

    // one pair
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

    // high card
    val sortedCard = cards.sortedDescending()
    val firstKicker = sortedCard[0]
    val secondKicker = sortedCard[1]
    val thirdKicker = sortedCard[2]
    val forthKicker = sortedCard[3]
    val fifthKicker = sortedCard[4]
    return HighCard(firstKicker, secondKicker, thirdKicker, forthKicker, fifthKicker)
  }

  companion object {

    private val REVERSE_COMPARATOR = Comparator<Rank> { a, b -> b.compareTo(a) }
  }
}