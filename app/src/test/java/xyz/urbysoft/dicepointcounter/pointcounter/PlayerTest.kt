package xyz.urbysoft.dicepointcounter.pointcounter

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.lang.IndexOutOfBoundsException
import kotlin.IllegalStateException

class PlayerTest {
    @Test
    fun testAddPoints() {
        val exceptedPointsHistory = listOf(5.5, 15.5)

        val player = Player("G0xilla")
            .addPoints(5.5)
            .addPoints(10.0)
        val actualPointsHistory = player.pointsHistory

        assertEquals(exceptedPointsHistory, actualPointsHistory)
    }

    @Test
    fun testGetPoints() {
        val exceptedPoints = 15.5

        val player = Player("G0xilla")
            .addPoints(5.5)
            .addPoints(10.0)
        val actualPoints = player.getPoints()

        assertEquals(exceptedPoints, actualPoints)
    }

    @Test
    fun testRevertPoints() {
        val exceptedPointsHistory = listOf(5.5)

        val player = Player("G0xilla")
            .addPoints(5.5)
            .addPoints(10.0)
            .revertPoints()
        val actualPointsHistory = player.pointsHistory

        assertEquals(exceptedPointsHistory, actualPointsHistory)
    }

    @Test
    fun whenPointsHistoryIsEmpty_thenThrowIllegalStateException() {
        val expectedException = IllegalStateException::class.java

        val player = Player("G0xilla")

        assertThrows(expectedException) {
            player.revertPoints()
        }
    }

    @Test
    fun testListAddPoints() {
        val expectedPlayerList = listOf(
            Player("G0xilla"),
            Player("Trenazery", listOf(10.5))
        )

        val actualPlayerList = listOf(
            Player("G0xilla"),
            Player("Trenazery")
        ).addPoints(1, 10.5)

        assertEquals(expectedPlayerList, actualPlayerList)
    }

    @Test
    fun testListGetPoints() {
        val expectedPoints = 10.5

        val actualPoints = listOf(
            Player("G0xilla"),
            Player("Trenazery")
        ).addPoints(1, 10.5)
            .getPoints(1)

        assertEquals(expectedPoints, actualPoints)
    }

    @Test
    fun testListRevertPoints() {
        val expectedPlayerList = listOf(
            Player("G0xilla"),
            Player("Trenazery", listOf(10.5))
        )

        val actualPlayerList = listOf(
            Player("G0xilla"),
            Player("Trenazery")
        ).addPoints(1, 10.5)
            .addPoints(1, 20.0)
            .revertPoints(1)

        assertEquals(actualPlayerList, expectedPlayerList)
    }

    @Test
    fun testAddPoints_whenPlayerIndexIsMoreThanOrEqualToTheSizeOfTheList_thenThrowIndexOutOfBoundsException() {
        val exceptedException = IndexOutOfBoundsException::class.java

        assertThrows(exceptedException) {
            listOf(Player("G0xilla")).addPoints(1, 20.0)
        }
    }

    @Test
    fun testGetPoints_whenPlayerIndexIsMoreThanOrEqualToTheSizeOfTheList_thenThrowIndexOutOfBoundsException() {
        val exceptedException = IndexOutOfBoundsException::class.java

        assertThrows(exceptedException) {
            listOf(Player("G0xilla")).getPoints(1)
        }
    }

    @Test
    fun testRevertPoints_whenPlayerHistoryIsEmpty_theThrowIllegalStateException() {
        val exceptedException = IllegalStateException::class.java

        assertThrows(exceptedException) {
            listOf(Player("G0xilla")).revertPoints(0)
        }
    }
}