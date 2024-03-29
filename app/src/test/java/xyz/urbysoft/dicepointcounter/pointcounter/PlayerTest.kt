package xyz.urbysoft.dicepointcounter.pointcounter

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException
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

        val playerOne = Player("G0xilla")
        val playerTwo = Player("Trenazery")
        val actualPlayerList = listOf(
            playerOne,
            playerTwo
        ).addPoints(playerTwo, 10.5)

        assertEquals(expectedPlayerList, actualPlayerList)
    }

    @Test
    fun testListGetPoints() {
        val expectedPoints = 10.5

        val playerOne = Player("G0xilla")
        val playerTwo = Player("Trenazery")
        val list = listOf(
            playerOne,
            playerTwo
        ).addPoints(playerOne, 10.5)
        val actualPoints = list[0].getPoints()

        assertEquals(expectedPoints, actualPoints)
    }

    @Test
    fun testListRevertPoints() {
        val expectedPlayerList = listOf(
            Player("G0xilla"),
            Player("Trenazery", listOf(10.5))
        )

        val playerOne = Player("G0xilla")
        val playerTwo = Player("Trenazery")
        var actualPlayerList = listOf(
            playerOne,
            playerTwo
        ).addPoints(playerTwo, 10.5)
        actualPlayerList = actualPlayerList.addPoints(actualPlayerList[1], 20.0)
        actualPlayerList = actualPlayerList.revertPoints(actualPlayerList[1])

        assertEquals(actualPlayerList, expectedPlayerList)
    }

    @Test
    fun testAddPoints_whenPlayerIsNotInTheList_thenThrowIllegalArgumentException() {
        val exceptedException = IllegalArgumentException::class.java


        assertThrows(exceptedException) {
            listOf(Player("G0xilla")).addPoints(Player("Trenazery"), 20.0)
        }
    }

    @Test
    fun testGetPoints_whenPlayerIsNotInTheList_thenThrowIllegalArgumentException() {
        val exceptedException = IllegalArgumentException::class.java

        assertThrows(exceptedException) {
            listOf(Player("G0xilla")).getPoints(Player("Trenazery"))
        }
    }

    @Test
    fun testRevertPoints_whenPlayerHistoryIsEmpty_theThrowIllegalStateException() {
        val exceptedException = IllegalStateException::class.java

        val player = Player("G0xilla")
        val list = listOf(player)
        assertThrows(exceptedException) {
            list.revertPoints(player)
        }
    }
}