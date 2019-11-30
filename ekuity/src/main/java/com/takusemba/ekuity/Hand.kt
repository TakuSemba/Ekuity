package com.takusemba.ekuity

import com.takusemba.ekuity.util.Quadruple

sealed class Hand : Comparable<Hand> {

  data class HighCard(
    private val firstKicker: Card,
    private val secondKicker: Card,
    private val thirdKicker: Card,
    private val forthKicker: Card,
    private val fifthKicker: Card
  ) : Hand() {

    init {
      require(firstKicker > secondKicker)
      require(secondKicker > thirdKicker)
      require(thirdKicker > forthKicker)
      require(forthKicker > fifthKicker)
    }

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is OnePair,
        is TwoPair,
        is Trips,
        is Straight,
        is Flush,
        is FullHouse,
        is Quads,
        is StraightFlush -> -1
        is HighCard -> {
          if (firstKicker.rank != other.firstKicker.rank) {
            return firstKicker.rank.compareTo(other.firstKicker.rank)
          }
          if (secondKicker.rank != other.secondKicker.rank) {
            return secondKicker.rank.compareTo(other.secondKicker.rank)
          }
          if (thirdKicker.rank != other.thirdKicker.rank) {
            return thirdKicker.rank.compareTo(other.thirdKicker.rank)
          }
          if (forthKicker.rank != other.forthKicker.rank) {
            return forthKicker.rank.compareTo(other.forthKicker.rank)
          }
          return fifthKicker.rank.compareTo(fifthKicker.rank)
        }
      }
    }
  }

  data class OnePair(
    private val pair: Pair<Card, Card>,
    private val firstKicker: Card,
    private val secondKicker: Card,
    private val thirdKicker: Card
  ) : Hand() {

    init {
      require(pair.first.rank == pair.second.rank)
      require(firstKicker > secondKicker)
      require(secondKicker > thirdKicker)
    }

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is HighCard -> 1
        is TwoPair,
        is Trips,
        is Straight,
        is Flush,
        is FullHouse,
        is Quads,
        is StraightFlush -> -1
        is OnePair -> {
          if (pair.first.rank != other.pair.first.rank) {
            return pair.first.rank.compareTo(other.pair.first.rank)
          }
          if (firstKicker.rank != other.firstKicker.rank) {
            return firstKicker.rank.compareTo(other.firstKicker.rank)
          }
          if (secondKicker.rank != other.secondKicker.rank) {
            return secondKicker.rank.compareTo(other.secondKicker.rank)
          }
          return thirdKicker.rank.compareTo(other.thirdKicker.rank)
        }
      }
    }
  }

  data class TwoPair(
    private val firstPair: Pair<Card, Card>,
    private val secondPair: Pair<Card, Card>,
    private val kicker: Card
  ) : Hand() {

    init {
      require(firstPair.first.rank == firstPair.second.rank)
      require(secondPair.first.rank == secondPair.second.rank)
    }

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is HighCard,
        is OnePair -> 1
        is Trips,
        is Straight,
        is Flush,
        is FullHouse,
        is Quads,
        is StraightFlush -> -1
        is TwoPair -> {
          if (firstPair.first.rank != other.firstPair.first.rank) {
            return firstPair.first.rank.compareTo(other.firstPair.first.rank)
          }
          if (secondPair.first.rank != other.secondPair.first.rank) {
            return secondPair.first.rank.compareTo(other.secondPair.first.rank)
          }
          return kicker.rank.compareTo(kicker.rank)
        }
      }
    }
  }

  data class Trips(
    private val triple: Triple<Card, Card, Card>,
    private val firstKicker: Card,
    private val secondKicker: Card
  ) : Hand() {

    init {
      require(triple.first.rank == triple.second.rank)
      require(triple.second.rank == triple.third.rank)
      require(firstKicker.rank > secondKicker.rank)
    }

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is HighCard,
        is OnePair,
        is TwoPair -> 1
        is Straight,
        is Flush,
        is FullHouse,
        is Quads,
        is StraightFlush -> -1
        is Trips -> {
          if (triple.first.rank != other.triple.first.rank) {
            return triple.first.rank.compareTo(other.triple.first.rank)
          }
          if (firstKicker.rank != other.firstKicker.rank) {
            return firstKicker.rank.compareTo(other.firstKicker.rank)
          }
          return secondKicker.rank.compareTo(other.secondKicker.rank)
        }
      }
    }
  }

  data class Straight(private val cards: List<Card>) : Hand() {

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is HighCard,
        is OnePair,
        is TwoPair,
        is Trips -> 1
        is Flush,
        is FullHouse,
        is Quads,
        is StraightFlush -> -1
        is Straight -> {
          return checkNotNull(cards.map { it.rank }.max()).compareTo(checkNotNull(other.cards.map { it.rank }.max()))
        }
      }
    }
  }

  data class Flush(private val cards: List<Card>) : Hand() {

    init {
      require(cards[0].suit == cards[1].suit)
      require(cards[1].suit == cards[2].suit)
      require(cards[2].suit == cards[3].suit)
      require(cards[3].suit == cards[4].suit)
    }

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is HighCard,
        is OnePair,
        is TwoPair,
        is Trips,
        is Straight -> 1
        is FullHouse,
        is Quads,
        is StraightFlush -> -1
        is Flush -> {
          return cards[0].rank.compareTo(other.cards[0].rank)
        }
      }
    }
  }

  data class FullHouse(
    private val triple: Triple<Card, Card, Card>,
    private val pair: Pair<Card, Card>
  ) : Hand() {

    init {
      require(triple.first.rank == triple.second.rank)
      require(triple.second.rank == triple.third.rank)
      require(pair.first.rank == pair.second.rank)
    }

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is HighCard,
        is OnePair,
        is TwoPair,
        is Trips,
        is Straight,
        is Flush -> 1
        is Quads,
        is StraightFlush -> -1
        is FullHouse -> {
          if (triple.first.rank != other.triple.first.rank) {
            return triple.first.rank.compareTo(other.triple.first.rank)
          }
          return pair.first.rank.compareTo(other.pair.first.rank)
        }
      }
    }
  }

  data class Quads(
    private val quadruple: Quadruple<Card, Card, Card, Card>,
    private val kicker: Card
  ) : Hand() {

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is HighCard,
        is OnePair,
        is TwoPair,
        is Trips,
        is Straight,
        is Flush,
        is FullHouse -> 1
        is StraightFlush -> -1
        is Quads -> {
          if (quadruple.first.rank != other.quadruple.first.rank) {
            return quadruple.first.rank.compareTo(other.quadruple.first.rank)
          }
          return kicker.rank.compareTo(other.kicker.rank)
        }
      }
    }
  }

  data class StraightFlush(private val cards: List<Card>) : Hand() {

    init {
      require(cards[0].suit == cards[1].suit)
      require(cards[1].suit == cards[2].suit)
      require(cards[2].suit == cards[3].suit)
      require(cards[3].suit == cards[4].suit)
    }

    override fun compareTo(other: Hand): Int {
      return when (other) {
        is HighCard,
        is OnePair,
        is TwoPair,
        is Trips,
        is Straight,
        is Flush,
        is FullHouse,
        is Quads -> 1
        is StraightFlush -> {
          return checkNotNull(cards.map { it.rank }.max()).compareTo(checkNotNull(other.cards.map { it.rank }.max()))
        }
      }
    }
  }
}